package com.desarrolloweb.redemprendedora.service;


import com.desarrolloweb.redemprendedora.dto.UsuarioRequestDTO;
import com.desarrolloweb.redemprendedora.dto.UsuarioResponseDTO;
import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.exeption.ResourceNotFoundException;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        mapearDtoAEntidad(dto, usuario);

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearEntidadADto(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearEntidadADto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return mapearEntidadADto(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        mapearDtoAEntidad(dto, usuario);

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapearEntidadADto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void mapearDtoAEntidad(UsuarioRequestDTO dto, Usuario usuario) {
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setContrasena(dto.getContrasena());
        usuario.setNombreEmprendimiento(dto.getNombreEmprendimiento());
        usuario.setTipoEmprendimiento(dto.getTipoEmprendimiento());
        usuario.setInstagram(dto.getInstagram());
        usuario.setIdUsuarioRol(dto.getIdUsuarioRol());
        usuario.setIdEvento(dto.getIdEvento());
        usuario.setIdLocalidad(dto.getIdLocalidad());
    }

    private UsuarioResponseDTO mapearEntidadADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccion(usuario.getDireccion());
        dto.setNombreEmprendimiento(usuario.getNombreEmprendimiento());
        dto.setTipoEmprendimiento(usuario.getTipoEmprendimiento());
        dto.setInstagram(usuario.getInstagram());
        dto.setIdUsuarioRol(usuario.getIdUsuarioRol());
        dto.setIdEvento(usuario.getIdEvento());
        dto.setIdLocalidad(usuario.getIdLocalidad());
        return dto;
    }
}