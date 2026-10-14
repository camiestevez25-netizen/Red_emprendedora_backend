package com.desarrolloweb.redemprendedora.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "localidad")
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
    private Long idLocalidad;

    @Column(name = "nombre_localidad", nullable = false)
    private String nombreLocalidad;

    @Column(name = "provincia_localidad", nullable = false)
    private String provinciaLocalidad;

    public Localidad() {}

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }

    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }

    public String getProvinciaLocalidad() { return provinciaLocalidad; }
    public void setProvinciaLocalidad(String provinciaLocalidad) { this.provinciaLocalidad = provinciaLocalidad; }
}