package com.desarrolloweb.redemprendedora.entity;

import com.desarrolloweb.redemprendedora.enums.RolEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario_rol")
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_rol")
    private Long idUsuarioRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 50)
    private RolEnum nombre;

    public UsuarioRol() {}

    public Long getIdUsuarioRol() { return idUsuarioRol; }
    public void setIdUsuarioRol(Long idUsuarioRol) { this.idUsuarioRol = idUsuarioRol; }

    public RolEnum getNombre() { return nombre; }
    public void setNombre(RolEnum nombre) { this.nombre = nombre; }
}