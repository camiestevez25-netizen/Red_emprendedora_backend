package com.desarrolloweb.redemprendedora.repository;

import com.desarrolloweb.redemprendedora.entity.UsuarioRol;
import com.desarrolloweb.redemprendedora.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    Optional<UsuarioRol> findByNombre(RolEnum nombre);
}