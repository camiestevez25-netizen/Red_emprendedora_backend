package com.desarrolloweb.redemprendedora.dto;

import lombok.Data;

@Data
public class StandFiltroDTO {
    private Integer idEvento;
    private Boolean estadoStand;
    private Integer numeroStand;
    private String nombreEvento;
    private Integer idUsuario;
}