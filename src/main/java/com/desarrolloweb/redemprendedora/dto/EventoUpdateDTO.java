package com.desarrolloweb.redemprendedora.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class EventoUpdateDTO {

    // Todos los campos son opcionales: solo se modifica lo que venga en el body

    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    private String fechaInicio;

    private String fechaFin;

    @PositiveOrZero(message = "El precio de la entrada no puede ser negativo")
    private BigDecimal precioEntrada;

    @Size(max = 25, message = "Los metros cuadrados no pueden superar los 25 caracteres")
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
