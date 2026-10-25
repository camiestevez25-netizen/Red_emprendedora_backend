package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.EventoRequestDTO;
import com.desarrolloweb.redemprendedora.dto.EventoResponseDTO;
import com.desarrolloweb.redemprendedora.dto.EventoUpdateDTO;
import com.desarrolloweb.redemprendedora.entity.Evento;
import com.desarrolloweb.redemprendedora.entity.Stand;
import com.desarrolloweb.redemprendedora.repository.StandRepository;
import com.desarrolloweb.redemprendedora.enums.EstadoEvento;
import com.desarrolloweb.redemprendedora.exception.ResourceNotFoundException;
import com.desarrolloweb.redemprendedora.exception.OperacionInvalidadException;
import com.desarrolloweb.redemprendedora.repository.EventoRepository;
import com.desarrolloweb.redemprendedora.entity.Localidad;
import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.enums.RolEnum;
import com.desarrolloweb.redemprendedora.repository.LocalidadRepository;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventoService {

    // Acepta "2026-9-20" y "2026-09-20", pero rechaza fechas inexistentes como 2026-02-31 o 2026-09-119
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT);

    // Palabras que quedan en minúscula dentro del nombre (salvo que sean la primera)
    private static final Set<String> PALABRAS_MENORES = Set.of(
            "de", "del", "la", "las", "el", "los", "y", "e", "o", "u",
            "en", "a", "al", "con", "por", "para");

    private final EventoRepository eventoRepository;
    private final StandRepository standRepository;
    private final LocalidadRepository localidadRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoService(EventoRepository eventoRepository,
                         StandRepository standRepository,
                         LocalidadRepository localidadRepository,
                         UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.standRepository = standRepository;
        this.localidadRepository = localidadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public EventoResponseDTO crear(EventoRequestDTO dto) {
        LocalDate inicio = parsearFecha(dto.getFechaInicio(), "fechaInicio");
        LocalDate fin = parsearFecha(dto.getFechaFin(), "fechaFin");
        validarRango(inicio, fin);

        Evento evento = new Evento();
        evento.setNombre(normalizarNombre(dto.getNombre()));
        evento.setFechaInicio(inicio.toString());
        evento.setFechaFin(fin.toString());
        evento.setEstado(EstadoEvento.ACTIVO.getValor());

        evento.setLocalidad(buscarLocalidad(dto.getIdLocalidad()));
        evento.setUsuario(buscarOrganizador(dto.getIdUsuario()));

        BigDecimal precio = (dto.getPrecioEntrada() != null) ? dto.getPrecioEntrada() : BigDecimal.ZERO;
        evento.setPrecioEntrada(precio);

        evento.setMetrosCuadrados(dto.getMetrosCuadrados().trim());

        Evento guardado = eventoRepository.save(evento);
        return mapearAResponse(guardado);
    }

    public List<EventoResponseDTO> listar(String estado, Long idLocalidad, Long idUsuario) {
        List<Evento> eventos;
        if (estado != null && !estado.isBlank()) {
            EstadoEvento estadoEvento = EstadoEvento.buscarPorValor(estado);
            if (estadoEvento == null) {
                throw new OperacionInvalidadException("El estado '" + estado + "' no es válido. Los estados válidos son: " + EstadoEvento.valoresValidos());
            }
            eventos = eventoRepository.findByEstadoIgnoreCase(estadoEvento.getValor());
        } else {
            eventos = eventoRepository.findAll();
        }

        return eventos.stream()
                // Si mandan idLocalidad, quedan solo los eventos de esa localidad
                .filter(e -> idLocalidad == null
                        || (e.getLocalidad() != null && e.getLocalidad().getIdLocalidad().equals(idLocalidad)))
                // Si mandan idUsuario, quedan solo los eventos de ese organizador
                .filter(e -> idUsuario == null
                        || (e.getUsuario() != null && e.getUsuario().getIdUsuario().equals(idUsuario)))
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public EventoResponseDTO obtenerPorId(Integer id) {
        Evento evento = buscarOFallar(id);
        return mapearAResponse(evento);
    }

    private Localidad buscarLocalidad(Long idLocalidad) {
        return localidadRepository.findById(idLocalidad)
                .orElseThrow(() -> new ResourceNotFoundException("Localidad no encontrada con id: " + idLocalidad));
    }

    // Busca el usuario y controla que tenga rol Organizador (RF-02)
    private Usuario buscarOrganizador(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + idUsuario));

        if (usuario.getUsuarioRol() == null || usuario.getUsuarioRol().getNombre() != RolEnum.ORGANIZADOR) {
            throw new OperacionInvalidadException("El usuario " + idUsuario + " no es Organizador: solo un Organizador puede crear eventos");
        }
        return usuario;
    }

    public EventoResponseDTO actualizar(Integer id, EventoUpdateDTO dto) {
        Evento evento = buscarOFallar(id);

        if (EstadoEvento.CANCELADO.getValor().equals(evento.getEstado())) {
            throw new OperacionInvalidadException("No se puede editar un evento cancelado");
        }

        if (dto.getNombre() != null) {
            if (dto.getNombre().isBlank()) {
                throw new OperacionInvalidadException("El nombre no puede estar vacío");
            }
            evento.setNombre(normalizarNombre(dto.getNombre()));
        }

        // Si se cambia solo una fecha, se compara contra la que ya estaba guardada
        if (dto.getFechaInicio() != null || dto.getFechaFin() != null) {
            LocalDate inicio = parsearFecha(
                    dto.getFechaInicio() != null ? dto.getFechaInicio() : evento.getFechaInicio(), "fechaInicio");
            LocalDate fin = parsearFecha(
                    dto.getFechaFin() != null ? dto.getFechaFin() : evento.getFechaFin(), "fechaFin");
            validarRango(inicio, fin);
            evento.setFechaInicio(inicio.toString());
            evento.setFechaFin(fin.toString());
        }

        if (dto.getPrecioEntrada() != null) evento.setPrecioEntrada(dto.getPrecioEntrada());

        if (dto.getMetrosCuadrados() != null) {
            if (dto.getMetrosCuadrados().isBlank()) {
                throw new OperacionInvalidadException("Los metros cuadrados no pueden estar vacíos");
            }
            String nuevosMetros = dto.getMetrosCuadrados().trim();
            if (!nuevosMetros.equals(evento.getMetrosCuadrados()) && tieneStandsReservados(evento)) {
                throw new OperacionInvalidadException(
                        "No se pueden cambiar los metros cuadrados: el evento ya tiene stands reservados");
            }
            evento.setMetrosCuadrados(nuevosMetros);
        }

        if (dto.getIdLocalidad() != null) {
            evento.setLocalidad(buscarLocalidad(dto.getIdLocalidad()));
        }

        Evento actualizado = eventoRepository.save(evento);
        return mapearAResponse(actualizado);
    }

    @Transactional
    public String eliminar(Integer id) {
        Evento evento = buscarOFallar(id);

        if (tieneStandsReservados(evento)) {
            evento.setEstado(EstadoEvento.CANCELADO.getValor());
            eventoRepository.save(evento);
            return "El evento tiene stands reservados, por eso se canceló en lugar de borrarse";
        }

        //Los stands libres no tienen historial: se borran junto con el evento
        List<Stand> standsLibres = standRepository.findByEventoId(id);
        standRepository.deleteAll(standsLibres);
        eventoRepository.delete(evento);
        return "Evento eliminado";
    }

    // Lo llama StandService cuando se reservva el último stand libre
    @Transactional
    public void marcarComoAgotado(Integer idEvento) {
        Evento evento = buscarOFallar(idEvento);

        if (EstadoEvento.CANCELADO.getValor().equals(evento.getEstado())) {
            throw new OperacionInvalidadException("No se puede reservar en un evento cancelado.");
        }

        if (EstadoEvento.AGOTADO.getValor().equals(evento.getEstado())) {
            return; // ya estaba agotado, no hay nada que cambiar.
        }

        evento.setEstado(EstadoEvento.AGOTADO.getValor());
        eventoRepository.save(evento);
    }

    //Lo llama StandService cuando se libera un stand de un evento agotado
    @Transactional
    public void reactivar(Integer idEvento) {
        Evento evento = buscarOFallar(idEvento);

        //Solo un evento agotado vuelve a estar activo. Si está cancelado, sigue cancelado.
        if (EstadoEvento.AGOTADO.getValor().equals(evento.getEstado())) {
            evento.setEstado(EstadoEvento.ACTIVO.getValor());
            eventoRepository.save(evento);
        }
    }

    private boolean tieneStandsReservados(Evento evento) {
        return standRepository.countOcupadosByEventoId(evento.getIdEvento()) > 0;
    }

    private Evento buscarOFallar(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
    }

    private LocalDate parsearFecha(String fecha, String campo) {
        try {
            return LocalDate.parse(fecha.trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new OperacionInvalidadException(
                    "La " + campo + " '" + fecha + "' no es una fecha válida. Formato esperado: aaaa-mm-dd");
        }
    }

    private void validarRango(LocalDate inicio, LocalDate fin) {
        if (inicio.isAfter(fin)) {
            throw new OperacionInvalidadException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
    }

    // "  feria   DE la PRIMAVERA " -> "Feria de la Primavera"
    private String normalizarNombre(String nombre) {
        String[] palabras = nombre.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT).split(" ");
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (i > 0) resultado.append(' ');
            if (i > 0 && PALABRAS_MENORES.contains(palabra)) {
                resultado.append(palabra);
            } else {
                resultado.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1));
            }
        }
        return resultado.toString();
    }

    private EventoResponseDTO mapearAResponse(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getIdEvento());
        dto.setNombre(evento.getNombre());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setFechaFin(evento.getFechaFin());
        dto.setEstado(evento.getEstado());
        dto.setPrecioEntrada(evento.getPrecioEntrada());

        boolean gratuita = evento.getPrecioEntrada().compareTo(BigDecimal.ZERO) == 0;
        dto.setEntradaGratuita(gratuita);
        dto.setPrecioEntradaTexto(gratuita ? "Entrada gratuita" : "$ " + evento.getPrecioEntrada().toPlainString());

        dto.setMetrosCuadrados(evento.getMetrosCuadrados());

        // Los eventos creados antes de este cambio no tienen localidad ni organizador
        if (evento.getLocalidad() != null) {
            dto.setIdLocalidad(evento.getLocalidad().getIdLocalidad());
            dto.setNombreLocalidad(evento.getLocalidad().getNombreLocalidad());
        }
        if (evento.getUsuario() != null) {
            dto.setIdUsuario(evento.getUsuario().getIdUsuario());
            dto.setNombreOrganizador(evento.getUsuario().getNombre());
        }
        return dto;
    }
}
