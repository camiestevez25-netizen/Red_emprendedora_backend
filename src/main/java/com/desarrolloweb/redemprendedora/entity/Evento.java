package com.desarrolloweb.redemprendedora.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer idEvento;

    @Column(name = "nombre_evento", nullable = false)
    private String nombreEvento;

    @Column(name = "estado_evento", nullable = false)
    private String estadoEvento = "Activo"; // activo, cancelado, finalizado
}