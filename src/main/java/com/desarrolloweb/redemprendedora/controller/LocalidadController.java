package com.desarrolloweb.redemprendedora.controller;
import jakarta.validation.Valid;
import com.desarrolloweb.redemprendedora.entity.Localidad;
import com.desarrolloweb.redemprendedora.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadController {

    @Autowired
    private LocalidadRepository localidadRepository;

    // GET /api/localidades - trae todas las localidades
    @GetMapping
    public List<Localidad> getAll() {
        return localidadRepository.findAll();
    }

    // GET /api/localidades/{id} - trae una localidad por id
    @GetMapping("/{id}")
    public ResponseEntity<Localidad> getById(@PathVariable Long id) {
        Optional<Localidad> localidad = localidadRepository.findById(id);
        return localidad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/localidades - crea una localidad nueva
    @PostMapping
    public Localidad create(@Valid @RequestBody Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    // PUT /api/localidades/{id} - actualiza una localidad existente
    @PutMapping("/{id}")
    public ResponseEntity<Localidad> update(@PathVariable Long id,@Valid @RequestBody Localidad datosNuevos) {
        return localidadRepository.findById(id)
                .map(localidad -> {
                    localidad.setNombreLocalidad(datosNuevos.getNombreLocalidad());
                    localidad.setProvinciaLocalidad(datosNuevos.getProvinciaLocalidad());
                    Localidad actualizado = localidadRepository.save(localidad);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/localidades/{id} - elimina una localidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!localidadRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localidadRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}