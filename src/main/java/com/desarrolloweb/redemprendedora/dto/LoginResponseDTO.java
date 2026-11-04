package com.desarrolloweb.redemprendedora.dto;

//Lo que devuelve el login: el token y los datos básicos que necesita el front
public class LoginResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private long expiraEnMs;
    private Long idUsuario;
    private String nombre;
    private String email;
    private String rol;

    public LoginResponseDTO() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public long getExpiraEnMs() { return expiraEnMs; }
    public void setExpiraEnMs(long expiraEnMs) { this.expiraEnMs = expiraEnMs; }

    public Long getIdUsuario() { return  idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) {this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
