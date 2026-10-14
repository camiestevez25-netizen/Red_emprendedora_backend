package com.desarrolloweb.redemprendedora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LocalidadDTO {

    private Long idLocalidad;

    @NotBlank(message = "El nombre de la localidad es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$",
            message = "El nombre de la localidad solo puede contener letras")
    private String nombreLocalidad;

    @NotBlank(message = "La provincia es obligatoria")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$",
            message = "La provincia solo puede contener letras")
    private String provinciaLocalidad;

    public LocalidadDTO() {}

    public LocalidadDTO(Long idLocalidad, String nombreLocalidad, String provinciaLocalidad) {
        this.idLocalidad = idLocalidad;
        this.nombreLocalidad = nombreLocalidad;
        this.provinciaLocalidad = provinciaLocalidad;
    }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }

    public String getProvinciaLocalidad() { return provinciaLocalidad; }
    public void setProvinciaLocalidad(String provinciaLocalidad) { this.provinciaLocalidad = provinciaLocalidad; }
}
