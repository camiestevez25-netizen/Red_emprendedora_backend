package com.desarrolloweb.redemprendedora.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar lo 50 caracteres")
    private String nombre;

    @NotBlank(message = "EL email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 50, message = "El email no puede superar lo 50 caracteres")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 50, message = "El teléfono no puede superar los 50 caracteres")
    private String telefono;

    @NotBlank(message = "La dirreción es obligatoria")
    @Size(max = 50, message = "La dirreción no puede superar los 50 caracteres")
    private String direccion;

    @Size(min = 6, max = 50, message = "La contraseña debe tener 6 y 50 caracteres")
    private String contrasena;

    @Size(max = 100, message = "El nombre del emprendimiento no puede superar los 100 caracteres")
    private String nombreEmprendimiento;

    @Size(max = 100, message = "El tipo del emprendimiento no puede superar los 100 caracteres")
    private String tipoEmprendimiento;

    @Size(max = 100, message = "El instagram no puede superar los 100 caracteres")
    private String instagram;

    private Long idUsuarioRol;
    private Long idLocalidad;

    // Getters y Setters
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

    public String getNombreEmprendimiento() { return nombreEmprendimiento; }
    public void setNombreEmprendimiento(String nombreEmprendimiento) { this.nombreEmprendimiento = nombreEmprendimiento; }

    public String getTipoEmprendimiento() { return tipoEmprendimiento; }
    public void setTipoEmprendimiento(String tipoEmprendimiento) { this.tipoEmprendimiento = tipoEmprendimiento; }

    public String getInstagram() { return instagram; }
    public void setInstagram(String instagram) { this.instagram = instagram; }

    public Long getIdUsuarioRol() { return idUsuarioRol; }
    public void setIdUsuarioRol(Long idUsuarioRol) { this.idUsuarioRol = idUsuarioRol; }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }
}