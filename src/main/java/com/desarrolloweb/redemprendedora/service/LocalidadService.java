package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.LocalidadDTO;
import com.desarrolloweb.redemprendedora.entity.Localidad;
import com.desarrolloweb.redemprendedora.repository.LocalidadRepository;
import com.desarrolloweb.redemprendedora.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalidadService {
    private final LocalidadRepository repository;

    public LocalidadService(LocalidadRepository repository) {
        this.repository = repository;
    }

    public List<LocalidadDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public LocalidadDTO obtenerPorId(Long id) {
        Localidad localidad = buscarEntidadPorId(id);
        return convertirADTO(localidad);
    }

    public LocalidadDTO crear(LocalidadDTO dto) {
        String nombre = dto.getNombreLocalidad().trim();
        String provincia = dto.getProvinciaLocalidad().trim();

        if (repository.existsByNombreLocalidadIgnoreCaseAndProvinciaLocalidadIgnoreCase(nombre, provincia)) {
            throw new IllegalArgumentException("Ya existe esa localidad en esa provincia");
        }
        Localidad localidad = new Localidad();
        localidad.setNombreLocalidad(nombre);
        localidad.setProvinciaLocalidad(provincia);
        return convertirADTO(repository.save(localidad));
    }

    public LocalidadDTO actualizar(Long id, LocalidadDTO dto) {
        Localidad localidad = buscarEntidadPorId(id);
        String nombre = dto.getNombreLocalidad().trim();
        String provincia = dto.getProvinciaLocalidad().trim();

        if (repository.existsByNombreLocalidadIgnoreCaseAndProvinciaLocalidadIgnoreCaseAndIdLocalidadNot(
                nombre, provincia, id)) {
            throw new IllegalArgumentException("Ya existe esa localidad en esa provincia");
        }
        localidad.setNombreLocalidad(nombre);
        localidad.setProvinciaLocalidad(provincia);
        return convertirADTO(repository.save(localidad));
    }

    public void eliminar(Long id) {
        Localidad localidad = buscarEntidadPorId(id);
        try {
            repository.delete(localidad);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "No se puede eliminar la localidad porque tiene usuarios o eventos asociados");
        }
    }
    private Localidad buscarEntidadPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Localidad no encontrada con id: " + id));
    }

    private LocalidadDTO convertirADTO(Localidad localidad) {
        return new LocalidadDTO(
                localidad.getIdLocalidad(),
                localidad.getNombreLocalidad(),
                localidad.getProvinciaLocalidad());
    }
}