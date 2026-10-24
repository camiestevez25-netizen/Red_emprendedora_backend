package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.StandFiltroDTO;
import com.desarrolloweb.redemprendedora.dto.StandRequestDTO;
import com.desarrolloweb.redemprendedora.dto.StandResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StandService {

    StandResponseDTO crearStand(StandRequestDTO standRequestDTO);

    Page<StandResponseDTO> listarStands(StandFiltroDTO filtro, Pageable pageable);

    StandResponseDTO obtenerStandPorId(Integer id);

    StandResponseDTO actualizarStand(Integer id, StandRequestDTO standRequestDTO);

    void eliminarStand(Integer id);

    StandResponseDTO obtenerEventoPorStand(Integer id);

    long contarStandsOcupadosPorEvento(Integer idEvento);

    boolean existeStandConNumeroEnEvento(Integer idEvento, Integer numeroStand);
}