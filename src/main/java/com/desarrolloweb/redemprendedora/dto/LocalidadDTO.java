package com.desarrolloweb.redemprendedora.dto;

public class LocalidadDTO {

    private Long idLocalidad;
    private String nombreLocalidad;
    private String provinciaLocalidad;

    public LocalidadDTO() {}

    public LocalidadDTO(Long idLocalidad, String nombreLocalidad, String provinciaLocalidad) {
        this.idLocalidad = idLocalidad;
        this.nombreLocalidad = nombreLocalidad;
        this.provinciaLocalidad = provinciaLocalidad;
    }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getNombreLocalidad() { return nombreLocalidad;}
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }

    public String getProvinciaLocalidad() { return provinciaLocalidad; }
    public void setProvinciaLocalidad(String provinciaLocalidad) { this.provinciaLocalidad = provinciaLocalidad; }
}
