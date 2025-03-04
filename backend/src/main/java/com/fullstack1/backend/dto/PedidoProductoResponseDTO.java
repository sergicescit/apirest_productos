package com.fullstack1.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fullstack1.backend.models.PedidoProducto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoProductoResponseDTO {

    private Long idPedidoProducto;
    private Long idPedido;
    private Long idProducto;
    private Integer cantidad;
    private LocalDateTime fechaPedido;
    private BigDecimal precioProducto;
    private Long idCliente;

    public PedidoProductoResponseDTO (PedidoProducto pedidoProducto) {
        this.idPedidoProducto = pedidoProducto.getIdPedidoProducto();
        this.idPedido = pedidoProducto.getPedido().getIdPedido();
        this.idProducto = pedidoProducto.getProducto().getIdProducto();
        this.cantidad = pedidoProducto.getCantidad();
        this.fechaPedido = pedidoProducto.getPedido().getFecha();
        this.precioProducto = pedidoProducto.getProducto().getPrecio();
        this.idCliente = pedidoProducto.getPedido().getCliente().getIdCliente();
    }

}
