package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.dto.UsuarioRequestDTO;
import com.desarrolloweb.redemprendedora.dto.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crear(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO obtenerPorId(Long id);
    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto);
    void eliminar(Long id);
}