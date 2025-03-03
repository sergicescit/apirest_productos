package com.fullstack1.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fullstack1.backend.dto.UsuarioRequestDTO;
import com.fullstack1.backend.dto.UsuarioResponseDTO;
import com.fullstack1.backend.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(usuarioDTO));
    }

    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(idUsuario, usuarioDTO));
    }

    @DeleteMapping("/eliminar/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
    }

    @GetMapping("/listar")
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/id/{idUsuario}")
    public ResponseEntity<?> listarUsuarioPorId(@PathVariable Long idUsuario) {
            Optional<UsuarioResponseDTO> usuarioOpt = usuarioService.listarUsuarioPorId(idUsuario);
            if (usuarioOpt.isPresent()) {
                return ResponseEntity.ok(usuarioOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado usuario con este id");
            }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> listarUsuarioPorEmail(@PathVariable String email) {
        Optional<UsuarioResponseDTO> usuarioOpt = usuarioService.listarUsuarioPorEmail(email);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado usuario con este email");
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> listarUsuarioPorNombre(@PathVariable String nombre) {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarioPorNombre(nombre);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado usuarios con este nombre");
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

}




