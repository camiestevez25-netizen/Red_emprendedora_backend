package com.desarrolloweb.redemprendedora.dto;

import com.desarrolloweb.redemprendedora.entity.Evento;

import java.math.BigDecimal;

public class EventoResponseDTO {

    private Integer id;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private BigDecimal precioEntrada;
    private boolean entradaGratuita;
    private String precioEntradaTexto;
    private String metrosCuadrados;

    public EventoResponseDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }

    public boolean isEntradaGratuita() { return entradaGratuita; }
    public void setEntradaGratuita(boolean entradaGratuita) { this.entradaGratuita = entradaGratuita; }

    public String getPrecioEntradaTexto() { return precioEntradaTexto; }
    public void setPrecioEntradaTexto(String precioEntradaTexto) { this.precioEntradaTexto = precioEntradaTexto; }

    public String getMetrosCuadrados() { return metrosCuadrados; }

    public void setMetrosCuadrados(String metrosCuadrados) { this.metrosCuadrados = metrosCuadrados; }
}
