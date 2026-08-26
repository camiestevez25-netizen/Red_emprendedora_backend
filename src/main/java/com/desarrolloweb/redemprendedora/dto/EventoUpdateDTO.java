package com.desarrolloweb.redemprendedora.dto;

import java.math.BigDecimal;

public class EventoUpdateDTO {

    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private BigDecimal precioEntrada;
    private String metrosCuadrados;

    public EventoUpdateDTO() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }

    public String getMetrosCuadrados() { return metrosCuadrados; }

    public void setMetrosCuadrados(String metrosCuadrados) { this.metrosCuadrados = metrosCuadrados; }
}
