package com.desarrolloweb.redemprendedora.dto;

public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String contrasena;
    private String nombreEmprendimiento;
    private String tipoEmprendimiento;
    private String instagram;
    private Long idUsuarioRol;
    private Long idEvento;
    private Long idLocalidad;

    //Getters y Setters
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
