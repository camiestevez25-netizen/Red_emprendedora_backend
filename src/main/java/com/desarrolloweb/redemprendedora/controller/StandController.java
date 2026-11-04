package com.desarrolloweb.redemprendedora.controller;

import com.desarrolloweb.redemprendedora.dto.StandFiltroDTO;
import com.desarrolloweb.redemprendedora.dto.StandRequestDTO;
import com.desarrolloweb.redemprendedora.dto.StandResponseDTO;
import com.desarrolloweb.redemprendedora.service.StandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/stands")
@RequiredArgsConstructor
public class StandController {

    private final StandService standService;

    @PostMapping
    @PreAuthorize("@seguridad.esDuenioEvento(#dto.idEvento, authentication)")
    public ResponseEntity<StandResponseDTO> crearStand(@Valid @RequestBody StandRequestDTO dto) {
        return new ResponseEntity<>(standService.crearStand(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<StandResponseDTO>> listarStands(
            StandFiltroDTO filtro,
            @PageableDefault(size = 10, sort = "idStand") Pageable pageable) {
        return ResponseEntity.ok(standService.listarStands(filtro, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandResponseDTO> obtenerStandPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(standService.obtenerStandPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@seguridad.esDuenioStand(#id, authentication) and @seguridad.esDuenioEvento(#dto.idEvento, authentication)")
    public ResponseEntity<StandResponseDTO> actualizarStand(
            @PathVariable Integer id,
            @Valid @RequestBody StandRequestDTO dto) {
        return ResponseEntity.ok(standService.actualizarStand(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@seguridad.esDuenioStand(#id, authentication)")
    public ResponseEntity<Void> eliminarStand(@PathVariable Integer id) {
        standService.eliminarStand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/evento")
    public ResponseEntity<StandResponseDTO> obtenerEventoPorStand(@PathVariable Integer id) {
        return ResponseEntity.ok(standService.obtenerEventoPorStand(id));
    }

    @GetMapping("/evento/{idEvento}/contar-ocupados")
    public ResponseEntity<Long> contarStandsOcupados(@PathVariable Integer idEvento) {
        return ResponseEntity.ok(standService.contarStandsOcupadosPorEvento(idEvento));
    }

    // PUT /api/stands/{id}/reservar  -> reserva a nombre del usuario logueado
    @PutMapping("/{id}/reservar")
    public ResponseEntity<StandResponseDTO> reservarStand(@PathVariable Integer id,
                                                          Authentication authentication) {
        return ResponseEntity.ok(standService.reservarStand(id, authentication.getName()));
    }

    // PUT /api/stands/{id}/cancelar-reserva
    @PutMapping("/{id}/cancelar-reserva")
    @PreAuthorize("@seguridad.puedeCancelarReserva(#id, authentication)")
    public ResponseEntity<StandResponseDTO> cancelarReserva(@PathVariable Integer id) {
        return ResponseEntity.ok(standService.cancelarReserva(id));
    }
}