package com.desarrolloweb.redemprendedora.config;

import com.desarrolloweb.redemprendedora.security.JwtAuthenticationFilter;
import com.desarrolloweb.redemprendedora.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableMethodSecurity // habilita @PreAuthorize en los controllers
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Lo usa AuthController para validar email y contraseña en el login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtService jwtService,
                                           UserDetailsService userDetailsService) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                // Sin sesiones: cada petición se autentica con el token
                .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        // Cualquier visitante puede ver ferias, localidad y stands (solo lectura)
                        .requestMatchers(HttpMethod.GET,
                                "/api/eventos", "/api/eventos/**",
                                "/api/localidades", "/api/localidades/**",
                                "/api/stands", "/api/stands/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/api/stands/*/reservar",
                                "/api/stands/*/cancelar-reserva")
                        .hasAnyRole("EMPRENDEDOR", "ORGANIZADOR")
                        .requestMatchers(HttpMethod.POST,
                                "/api/eventos", "/api/stands", "/api/localidades")
                        .hasRole("ORGANIZADOR")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/eventos/**", "/api/stands/**", "/api/localidades/**")
                        .hasRole("ORGANIZADOR")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/eventos/**", "/api/stands/**", "/api/localidades/**")
                        .hasRole("ORGANIZADOR")
                        .anyRequest().authenticated())
                // Respuestas en JSON cuando falta el token (401) o no tiene permiso (403)
                .exceptionHandling(errores -> errores
                        .authenticationEntryPoint((request, response, ex) ->
                                escribirError(response, HttpServletResponse.SC_UNAUTHORIZED,
                                        "Tenés que iniciar sesión para acceder a este recurso"))
                        .accessDeniedHandler((request, response, ex) ->
                                escribirError(response, HttpServletResponse.SC_FORBIDDEN,
                                        "No tenés permiso para realizar esta acción")))
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void escribirError(HttpServletResponse response, int status, String mensaje) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":" + status + ",\"mensaje\":\"" + mensaje + "\"}");
    }
}