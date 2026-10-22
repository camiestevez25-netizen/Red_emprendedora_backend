package com.desarrolloweb.redemprendedora.controller;

import com.desarrolloweb.redemprendedora.dto.LocalidadDTO;
import com.desarrolloweb.redemprendedora.service.LocalidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadController {

    private final LocalidadService localidadService;

    public LocalidadController(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    // GET /api/localidades - trae todas las localidades
    @GetMapping
    public ResponseEntity<List<LocalidadDTO>> obtenerTodas() {
        return ResponseEntity.ok(localidadService.obtenerTodas());
    }

    // GET /api/localidades/{id} - trae una localidad por id
    @GetMapping("/{id}")
    public ResponseEntity<LocalidadDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localidadService.obtenerPorId(id));
    }

    // POST /api/localidades - crea una localidad nueva
    @PostMapping
    public ResponseEntity<LocalidadDTO> crear(@Valid @RequestBody LocalidadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localidadService.crear(dto));
    }

    // PUT /api/localidades/{id} - actualiza una localidad existente
    @PutMapping("/{id}")
    public ResponseEntity<LocalidadDTO> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody LocalidadDTO dto) {
        return ResponseEntity.ok(localidadService.actualizar(id, dto));
    }

    // DELETE /api/localidades/{id} - elimina una localidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        localidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}