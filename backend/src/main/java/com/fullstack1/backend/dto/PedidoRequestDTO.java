package com.fullstack1.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoRequestDTO {

    private Long idPedido;
    
    @NotNull(message = "El id cliente es obligatorioa")
    private Long clienteId;
}
