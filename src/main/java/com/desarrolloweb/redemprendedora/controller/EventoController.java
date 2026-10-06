package com.desarrolloweb.redemprendedora.controller;

import com.desarrolloweb.redemprendedora.dto.EventoRequestDTO;
import com.desarrolloweb.redemprendedora.dto.EventoResponseDTO;
import com.desarrolloweb.redemprendedora.dto.EventoUpdateDTO;
import com.desarrolloweb.redemprendedora.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> crear(@Valid @RequestBody EventoRequestDTO dto) {
        EventoResponseDTO creado = eventoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listar(
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(eventoService.listar(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(eventoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EventoUpdateDTO dto) {
        return ResponseEntity.ok(eventoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Integer id) {
        String mensaje = eventoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }


}
