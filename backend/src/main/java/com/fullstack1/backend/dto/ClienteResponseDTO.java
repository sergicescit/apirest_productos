package com.fullstack1.backend.dto;


import com.fullstack1.backend.models.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long idCliente;
    private String nombre;
    private String email;
    private String password;

    public ClienteResponseDTO (Cliente cliente) {
        this.idCliente = cliente.getIdCliente();
        this.nombre = cliente.getNombre();
        this.email = cliente.getEmail();
        this.password = cliente.getPassword();
    }

}
