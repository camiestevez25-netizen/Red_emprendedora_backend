package com.desarrolloweb.redemprendedora.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StandResponseDTO {
    private Integer idStand;
    private BigDecimal costoStand;
    private Boolean estadoStand;
    private String estadoStandTexto;
    private Integer numeroStand;

    private Integer idEvento;
    private String nombreEvento;
    private String estadoEvento;

    private Long idUsuario;
    private String nombreUsuario;
    private String nombreEmprendimiento;
}