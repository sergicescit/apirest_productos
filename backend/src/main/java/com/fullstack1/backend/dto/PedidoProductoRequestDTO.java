package com.fullstack1.backend.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PedidoProductoRequestDTO {

    @NotNull(message = "El id de pedido es obligatorio")
    private Long idPedido;

    @NotNull(message = "El id de producto es obligatorio")
    private Long idProducto;

    @NonNull
    @Min(value = 1, message = "La cantidad ha de ser mayor a 0")
    private Integer cantidad;
}
