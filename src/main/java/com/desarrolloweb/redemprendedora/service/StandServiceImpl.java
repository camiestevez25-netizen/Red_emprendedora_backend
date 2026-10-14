package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.StandFiltroDTO;
import com.desarrolloweb.redemprendedora.dto.StandRequestDTO;
import com.desarrolloweb.redemprendedora.dto.StandResponseDTO;
import com.desarrolloweb.redemprendedora.entity.Evento;
import com.desarrolloweb.redemprendedora.entity.Stand;
import com.desarrolloweb.redemprendedora.enums.EstadoStand;
import com.desarrolloweb.redemprendedora.exception.BusinessRuleException;
import com.desarrolloweb.redemprendedora.exception.ResourceNotFoundException;
import com.desarrolloweb.redemprendedora.repository.EventoRepository;
import com.desarrolloweb.redemprendedora.repository.StandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StandServiceImpl implements StandService {

    private final StandRepository standRepository;
    private final EventoRepository eventoRepository;

    @Override
    @Transactional
    public StandResponseDTO crearStand(StandRequestDTO standRequestDTO) {
        // Validar que el evento existe
        Evento evento = eventoRepository.findById(standRequestDTO.getIdEvento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evento no encontrado con ID: " + standRequestDTO.getIdEvento()));

        // Validar que el evento no esté cancelado
        if ("Cancelado".equals(evento.getEstadoEvento())) {
            throw new BusinessRuleException("No se puede crear stands en un evento cancelado");
        }

        // Validar que el número de stand es único para el evento
        if (standRepository.findByEventoIdEventoAndNumeroStand(evento.getIdEvento(), standRequestDTO.getNumeroStand()).isPresent()) {
            throw new BusinessRuleException(
                    "El número de stand " + standRequestDTO.getNumeroStand() + " ya existe para este evento");
        }

        // Crear stand
        Stand stand = new Stand();
        stand.setCostoStand(standRequestDTO.getCostoStand());
        stand.setNumeroStand(standRequestDTO.getNumeroStand());
        stand.setEstadoStand(true); // Disponible por defecto
        stand.setEvento(evento);

        Stand savedStand = standRepository.save(stand);
        return convertirAResponseDTO(savedStand);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StandResponseDTO> listarStands(StandFiltroDTO filtro, Pageable pageable) {
        Specification<Stand> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getIdEvento() != null) {
                predicates.add(criteriaBuilder.equal(root.get("evento").get("idEvento"), filtro.getIdEvento()));
            }

            if (filtro.getEstadoStand() != null) {
                predicates.add(criteriaBuilder.equal(root.get("estadoStand"), filtro.getEstadoStand()));
            }

            if (filtro.getNumeroStand() != null) {
                predicates.add(criteriaBuilder.equal(root.get("numeroStand"), filtro.getNumeroStand()));
            }

            if (filtro.getNombreEvento() != null && !filtro.getNombreEvento().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("evento").get("nombre")),
                        "%" + filtro.getNombreEvento().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return standRepository.findAll(spec, pageable)
                .map(this::convertirAResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public StandResponseDTO obtenerStandPorId(Integer id) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stand no encontrado con ID: " + id));
        return convertirAResponseDTO(stand);
    }

    @Override
    @Transactional
    public StandResponseDTO actualizarStand(Integer id, StandRequestDTO standRequestDTO) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stand no encontrado con ID: " + id));

        // Validar que el evento existe
        Evento evento = eventoRepository.findById(standRequestDTO.getIdEvento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evento no encontrado con ID: " + standRequestDTO.getIdEvento()));

        // Validar que el evento no esté cancelado
        if ("Cancelado".equals(evento.getEstadoEvento())) {
            throw new BusinessRuleException("No se puede modificar stands en un evento cancelado");
        }

        // Si el stand está ocupado (estadoStand = false), no permitir cambios
        if (!stand.getEstadoStand()) {
            throw new BusinessRuleException("No se puede modificar un stand que está ocupado");
        }

        // Validar que el número de stand es único para el evento (excepto el mismo stand)
        Stand existingStand = standRepository.findByEventoIdEventoAndNumeroStand(
                evento.getIdEvento(), standRequestDTO.getNumeroStand()).orElse(null);
        if (existingStand != null && !existingStand.getIdStand().equals(id)) {
            throw new BusinessRuleException(
                    "El número de stand " + standRequestDTO.getNumeroStand() + " ya existe para este evento");
        }

        // Actualizar datos
        stand.setCostoStand(standRequestDTO.getCostoStand());
        stand.setNumeroStand(standRequestDTO.getNumeroStand());
        stand.setEvento(evento);
        // Mantener el estado actual (no se puede cambiar el estado en PUT)

        Stand updatedStand = standRepository.save(stand);
        return convertirAResponseDTO(updatedStand);
    }

    @Override
    @Transactional
    public void eliminarStand(Integer id) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stand no encontrado con ID: " + id));

        // Validar que el stand no esté ocupado
        if (!stand.getEstadoStand()) {
            throw new BusinessRuleException("No se puede eliminar un stand que está ocupado");
        }

        // Baja física: el stand está libre, no hay historial que conservar
        standRepository.delete(stand);
    }

    @Override
    @Transactional(readOnly = true)
    public StandResponseDTO obtenerEventoPorStand(Integer id) {
        Stand stand = standRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stand no encontrado con ID: " + id));
        return convertirAResponseDTO(stand);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarStandsOcupadosPorEvento(Integer idEvento) {
        return standRepository.countOcupadosByEventoId(idEvento);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeStandConNumeroEnEvento(Integer idEvento, Integer numeroStand) {
        return standRepository.findByEventoIdEventoAndNumeroStand(idEvento, numeroStand).isPresent();
    }

    private StandResponseDTO convertirAResponseDTO(Stand stand) {
        StandResponseDTO dto = new StandResponseDTO();
        dto.setIdStand(stand.getIdStand());
        dto.setCostoStand(stand.getCostoStand());
        dto.setEstadoStand(stand.getEstadoStand());
        dto.setEstadoStandTexto(EstadoStand.getEstadoTexto(stand.getEstadoStand()));
        dto.setNumeroStand(stand.getNumeroStand());

        if (stand.getEvento() != null) {
            dto.setIdEvento(stand.getEvento().getIdEvento());
            dto.setNombreEvento(stand.getEvento().getNombreEvento());
            dto.setEstadoEvento(stand.getEvento().getEstadoEvento());
        }

        return dto;
    }
}