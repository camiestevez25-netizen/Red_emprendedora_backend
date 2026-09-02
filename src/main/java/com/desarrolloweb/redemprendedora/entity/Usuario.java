package com.desarrolloweb.redemprendedora.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "usuario")
public class Usuario {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", length = 50, nullable =false)
    private String nombre;

    @Column(name = "email", length = 15, nullable = false)
    private String email;

    @Column(name = "telefono", length = 50, nullable = false)
    private String telefono;

    @Column(name = "direccion", length = 50, nullable = false)
    private String direccion;

    @Column(name = "contrasena", length = 50, nullable = false)
    private String contrasena;

    @Column(name = "nombre_emprendimiento", length = 100)
    private String nombreEmprendimiento;

    @Column(name = "tipo_emprendimiento", length = 100)
    private String tipoEmprendimiento;

    @Column(name = "instagram", length = 100)
    private String instagram;

    @Column(name = "id_usuario_rol")
    private Long idUsuarioRol;

    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "id_localidad")
    private Long idLocalidad;

    public Usuario(){

    }
    //Getters y Setters
    public Long getIdUsuario(){ return idUsuario; }
    public void setIdUsuario(Long idUsuario) {this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreEmprendimiento() {return nombreEmprendimiento; }
    public void setNombreEmprendimiento(String nombreEmprendimiento) { this.nombreEmprendimiento = nombreEmprendimiento;}

    public String getTipoEmprendimiento() { return tipoEmprendimiento; }
    public void setTipoEmprendimiento(String tipoEmprendimiento) { this.tipoEmprendimiento = tipoEmprendimiento; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public Long getIdUsuarioRol() { return idUsuarioRol; }
    public void setIdUsuarioRol( Long idUsuarioRol) { this.idUsuarioRol = idUsuarioRol; }

    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }
}
