package com.desarrolloweb.redemprendedora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/roles").permitAll()
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
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}