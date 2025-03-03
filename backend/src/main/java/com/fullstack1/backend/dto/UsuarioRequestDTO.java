package com.fullstack1.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioRequestDTO {

    private Long idUsuario;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El email es obligatorio")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    private String password;

}

