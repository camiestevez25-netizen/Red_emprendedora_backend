package redemprendedora;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StandRequestDTO {

    @NotNull(message = "El costo del stand es obligatorio")
    @DecimalMin(value = "0.01", message = "El costo debe ser mayor a 0")
    @DecimalMax(value = "99999999.99", message = "El costo no puede ser tan alto")
    private BigDecimal costoStand;

    @NotNull(message = "El número de stand es obligatorio")
    @Min(value = 1, message = "El número de stand debe ser positivo")
    @Max(value = 9999, message = "El número de stand no puede ser tan alto")
    private Integer numeroStand;

    @NotNull(message = "El ID del evento es obligatorio")
    @Positive(message = "El ID del evento debe ser positivo")
    private Integer idEvento;
}