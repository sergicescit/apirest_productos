package com.fullstack1.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoRequestDTO {

    @NotNull(message = "El nombre de producto es obligatorio")
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio")
    private BigDecimal precio;

}
