package com.desarrolloweb.redemprendedora.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StandRequestDTO {

    @NotNull(message = "El costo del stand es obligatorio")
    @DecimalMin(value = "0.01", message = "El costo debe ser mayor a 0")
    private BigDecimal costoStand;

    @NotNull(message = "El número de stand es obligatorio")
    @Min(value = 1, message = "El número de stand debe ser positivo")
    private Integer numeroStand;

    @NotNull(message = "El ID del evento es obligatorio")
    @Positive(message = "El ID del evento debe ser positivo")
    private Integer idEvento;

    @Positive(message = "El ID del usuario debe ser positivo")
    private Integer idUsuario;
}