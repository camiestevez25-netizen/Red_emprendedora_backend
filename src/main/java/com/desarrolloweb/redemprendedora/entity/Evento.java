package com.desarrolloweb.redemprendedora.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false, length = 25)
    private String fechaInicio;

    @Column(name = "fecha_fin", nullable = false, length = 25)
    private String fechaFin;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "precio_entrada", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioEntrada;

    @Column(name = "metros_cuadrados", nullable = false, length = 25)
    private String metrosCuadrados;

    @ManyToOne
    @JoinColumn(name = "id_localidad")
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Evento() {
    }

    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }

    public String getMetrosCuadrados() { return metrosCuadrados; }

    public void setMetrosCuadrados(String metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

}