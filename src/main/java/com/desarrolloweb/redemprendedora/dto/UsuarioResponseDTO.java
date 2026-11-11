package com.desarrolloweb.redemprendedora.dto;

import java.util.List;

public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String nombreEmprendimiento;
    private String tipoEmprendimiento;
    private String instagram;
    private List<String> roles;
    private Long idLocalidad;
    private String nombreLocalidad;

    public UsuarioResponseDTO() {
    }

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNombreEmprendimiento() { return nombreEmprendimiento; }
    public void setNombreEmprendimiento(String nombreEmprendimiento) { this.nombreEmprendimiento = nombreEmprendimiento; }

    public String getTipoEmprendimiento() { return tipoEmprendimiento; }
    public void setTipoEmprendimiento(String tipoEmprendimiento) { this.tipoEmprendimiento = tipoEmprendimiento; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }
}