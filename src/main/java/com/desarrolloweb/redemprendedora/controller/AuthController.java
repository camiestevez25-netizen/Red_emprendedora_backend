package com.desarrolloweb.redemprendedora.controller;

import com.desarrolloweb.redemprendedora.dto.LoginRequestDTO;
import com.desarrolloweb.redemprendedora.dto.LoginResponseDTO;
import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.exception.ResourceNotFoundException;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import com.desarrolloweb.redemprendedora.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    // POST /api/auth/login  { "email": "...", "contrasena": "..." }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        // Valida email y contraseña con BCrypt. Si están mal tira BadCredentialsException (401)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, dto.getContrasena()));

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        LoginResponseDTO respuesta = new LoginResponseDTO();
        respuesta.setToken(jwtService.generarToken(usuario.getEmail()));
        respuesta.setExpiraEnMs(jwtService.getExpiracionMs());
        respuesta.setIdUsuario(usuario.getIdUsuario());
        respuesta.setNombre(usuario.getNombre());
        respuesta.setEmail(usuario.getEmail());
        if (usuario.getUsuarioRol() != null) {
            respuesta.setRol(usuario.getUsuarioRol().getNombre().name());
        }
        return ResponseEntity.ok(respuesta);
    }
}
