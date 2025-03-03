package com.fullstack1.backend.dto;

import com.fullstack1.backend.models.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String password;

    public UsuarioResponseDTO (Usuario usuario){
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
    }
    public UsuarioResponseDTO (Long idUsuario, String nombre, String email){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;        
    }
}

