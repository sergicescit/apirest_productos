package com.fullstack1.backend.dto;

import java.time.LocalDateTime;

import com.fullstack1.backend.models.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoResponseDTO {
    private Long idPedido;
    private Long clienteId;
    private LocalDateTime fecha;

    public PedidoResponseDTO (Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.clienteId = pedido.getCliente().getIdCliente();
        this.fecha = pedido.getFecha();
    }
}
