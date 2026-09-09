package com.desarrolloweb.redemprendedora.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;

@Entity
@Table(name = "localidad")
 public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
    private Long idLocalidad;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", message = "El nombre de la localidad solo puede contener letras o no puede estar vacío")
    @Column(name = "nombre_localidad", nullable = false)
    private String nombreLocalidad;

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", message = "El nombre de la provincia solo puede contener letras o no puede estar vacío")
    @Column(name = "provincia_localidad", nullable = false)
    private String provinciaLocalidad;

    public Localidad(){}

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad){ this.idLocalidad = idLocalidad; }

    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }

    public String getProvinciaLocalidad() {return provinciaLocalidad;}
    public void setProvinciaLocalidad(String provinciaLocalidad) {this.provinciaLocalidad = provinciaLocalidad; }
 }

