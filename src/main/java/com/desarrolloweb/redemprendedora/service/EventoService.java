package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.EventoRequestDTO;
import com.desarrolloweb.redemprendedora.dto.EventoResponseDTO;
import com.desarrolloweb.redemprendedora.entity.Evento;
import com.desarrolloweb.redemprendedora.enums.EstadoEvento;
import com.desarrolloweb.redemprendedora.exception.NoEncontradoException;
import com.desarrolloweb.redemprendedora.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public EventoResponseDTO crear(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setNombre(dto.getNombre());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setEstado(EstadoEvento.ACTIVO.getValor());

        BigDecimal precio = (dto.getPrecioEntrada() != null) ? dto.getPrecioEntrada() : BigDecimal.ZERO;
        evento.setPrecioEntrada(precio);

        evento.setMetrosCuadrados(dto.getMetrosCuadrados());

        Evento guardado = eventoRepository.save(evento);
        return mapearAResponse(guardado);
    }

    public List<EventoResponseDTO> listar(String estado) {
        List<Evento> eventos = (estado != null)
                ? eventoRepository.findByEstado(estado)
                : eventoRepository.findAll();

        return eventos.stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public EventoResponseDTO obtenerPorId(Integer id) {
        Evento evento = buscarOFallar(id);
        return mapearAResponse(evento);
    }

    public EventoResponseDTO actualizar(Integer id, EventoRequestDTO dto) {
        Evento evento = buscarOFallar(id);

        if (dto.getNombre() != null) evento.setNombre(dto.getNombre());
        if (dto.getFechaInicio() != null) evento.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) evento.setFechaFin(dto.getFechaFin());
        if (dto.getPrecioEntrada() != null) evento.setPrecioEntrada(dto.getPrecioEntrada());
        if (dto.getMetrosCuadrados() != null) evento.setMetrosCuadrados(dto.getMetrosCuadrados());

        Evento actualizado = eventoRepository.save(evento);
        return mapearAResponse(actualizado);
    }

    public void cancelar(Integer id) {
        Evento evento = buscarOFallar(id);
        evento.setEstado(EstadoEvento.CANCELADO.getValor());
        eventoRepository.save(evento);
    }

    private Evento buscarOFallar(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Evento no encontrado con id: " + id));
    }

    private EventoResponseDTO mapearAResponse(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getId());
        dto.setNombre(evento.getNombre());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setFechaFin(evento.getFechaFin());
        dto.setEstado(evento.getEstado());
        dto.setPrecioEntrada(evento.getPrecioEntrada());
        dto.setMetrosCuadrados(evento.getMetrosCuadrados());
        return dto;
    }
}