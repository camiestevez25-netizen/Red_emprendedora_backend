package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.UsuarioRequestDTO;
import com.desarrolloweb.redemprendedora.dto.UsuarioResponseDTO;
import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.exception.ResourceNotFoundException;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
        String email = dto.getEmail().trim().toLowerCase();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (dto.getContrasena() == null || dto.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Usuario usuario = new Usuario();
        mapearDtoAEntidad(dto, usuario);
        usuario.setEmail(email);
        usuario.setContrasena(dto.getContrasena());

        return mapearEntidadADto(usuarioRepository.save(usuario));
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

        String email = dto.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmailIgnoreCaseAndIdUsuarioNot(email, id)) {
            throw new IllegalArgumentException("Ya existe otro usuario con ese email");
        }

        mapearDtoAEntidad(dto, usuario);
        usuario.setEmail(email);
        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuario.setContrasena(dto.getContrasena());
        }

        return mapearEntidadADto(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "No se puede eliminar el usuario porque tiene eventos, stands o roles asociados");
        }
    }

    private void mapearDtoAEntidad(UsuarioRequestDTO dto, Usuario usuario) {
        usuario.setNombre(dto.getNombre());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setNombreEmprendimiento(dto.getNombreEmprendimiento());
        usuario.setTipoEmprendimiento(dto.getTipoEmprendimiento());
        usuario.setInstagram(dto.getInstagram());
        usuario.setIdUsuarioRol(dto.getIdUsuarioRol());
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
        dto.setIdLocalidad(usuario.getIdLocalidad());
        return dto;
    }
}