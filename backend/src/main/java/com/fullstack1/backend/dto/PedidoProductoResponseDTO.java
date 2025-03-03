package com.fullstack1.backend.dto;

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

    public PedidoProductoResponseDTO (PedidoProducto pedidoProducto) {
        this.idPedidoProducto = pedidoProducto.getIdPedidoProducto();
        this.idPedido = pedidoProducto.getPedido().getIdPedido();
        this.idProducto = pedidoProducto.getProducto().getIdProducto();
        this.cantidad = pedidoProducto.getCantidad();
    }

}
