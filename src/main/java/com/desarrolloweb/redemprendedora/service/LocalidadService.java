package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.LocalidadDTO;
import com.desarrolloweb.redemprendedora.entity.Localidad;
import com.desarrolloweb.redemprendedora.repository.LocalidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalidadService {
    private final LocalidadRepository repository;

    public LocalidadService(LocalidadRepository repository){
        this.repository = repository;
    }

    public List<LocalidadDTO> obtenerTodas() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public  LocalidadDTO obtenerPorId(Long id) {
        Localidad localidad = buscarEntidadPorId(id);
        return convertirADTO(localidad);
    }

    public LocalidadDTO crear(LocalidadDTO dto) {
        boolean existe = repository.existsByNombreLocalidadAndProvinciaLocalidad(
                dto.getNombreLocalidad(), dto.getProvinciaLocalidad());
        if (existe) {
            throw new RuntimeException("Ya existe esa localidad en esa provincia");
        }
        Localidad localidad = new Localidad();
        localidad.setNombreLocalidad(dto.getNombreLocalidad());
        localidad.setProvinciaLocalidad(dto.getProvinciaLocalidad());
        return convertirADTO(repository.save(localidad));

    }
    public LocalidadDTO actualizar(Long id, LocalidadDTO dto) {
        Localidad localidad = buscarEntidadPorId(id);
        localidad.setNombreLocalidad(dto.getNombreLocalidad());
        localidad.setProvinciaLocalidad(dto.getProvinciaLocalidad());
        return convertirADTO(repository.save(localidad));
    }

    public void eliminar(Long id) {
        Localidad localidad = buscarEntidadPorId(id);
        repository.delete(localidad);
    }
    private Localidad buscarEntidadPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));
    }

    private LocalidadDTO convertirADTO(Localidad localidad) {
        return new LocalidadDTO(
                localidad.getIdLocalidad(),
                localidad.getNombreLocalidad(),
                localidad.getProvinciaLocalidad());
    }

}
