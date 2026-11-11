package com.desarrolloweb.redemprendedora.entity;

import com.desarrolloweb.redemprendedora.enums.RolEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "telefono", length = 15, nullable = false)
    private String telefono;

    @Column(name = "direccion", length = 50, nullable = false)
    private String direccion;

    @Column(name = "contrasena", length = 100, nullable = false)
    private String contrasena;

    @Column(name = "nombre_emprendimiento", length = 100)
    private String nombreEmprendimiento;

    @Column(name = "tipo_emprendimiento", length = 100)
    private String tipoEmprendimiento;

    @Column(name = "instagram", length = 100)
    private String instagram;

    // Un usuario puede ser ORGANIZADOR, EMPRENDEDOR o las dos cosas.
    // EAGER porque los roles se leen fuera de una transaccion (login y filtro JWT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_x_rol",
    joinColumns = @JoinColumn(name = "id_usuario"),
    inverseJoinColumns = @JoinColumn(name = "id_usuario_rol"))
    private Set<UsuarioRol> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    public Usuario() {
    }

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreEmprendimiento() {
        return nombreEmprendimiento;
    }

    public void setNombreEmprendimiento(String nombreEmprendimiento) {
        this.nombreEmprendimiento = nombreEmprendimiento;
    }

    public String getTipoEmprendimiento() {
        return tipoEmprendimiento;
    }

    public void setTipoEmprendimiento(String tipoEmprendimiento) {
        this.tipoEmprendimiento = tipoEmprendimiento;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Set<UsuarioRol> getRoles() { return roles; }
    public void setRoles(Set<UsuarioRol> roles) { this.roles = roles; }

    public boolean tieneRol(RolEnum rol) {
        return roles.stream().anyMatch(r -> r.getNombre() == rol);
    }

    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
}