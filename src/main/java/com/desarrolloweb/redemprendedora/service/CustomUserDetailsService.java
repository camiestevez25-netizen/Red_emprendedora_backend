package com.desarrolloweb.redemprendedora.service;

import com.desarrolloweb.redemprendedora.entity.Usuario;
import com.desarrolloweb.redemprendedora.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con email: " + email));

        // Sin rol: puede iniciar sesión pero solo leer
        List<GrantedAuthority> autoridades = usuario.getUsuarioRol() == null
                ? List.of()
                : List.of(new SimpleGrantedAuthority(
                "ROLE_" + usuario.getUsuarioRol().getNombre().name()));

        return new User(usuario.getEmail(), usuario.getContrasena(), autoridades);
    }
}