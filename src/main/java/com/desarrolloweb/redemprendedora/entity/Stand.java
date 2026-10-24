package com.desarrolloweb.redemprendedora.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stand")
public class Stand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stand")
    private Integer idStand;

    @Column(name = "costo_stand", precision = 10, scale = 2)
    private BigDecimal costoStand;

    @Column(name = "estado_stand", nullable = false)
    private Boolean estadoStand = true; // true = disponible, false = ocupado

    @Column(name = "numero_stand", nullable = false)
    private Integer numeroStand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}