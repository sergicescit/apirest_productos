package com.fullstack1.backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.models.Usuario;
import com.fullstack1.backend.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import com.fullstack1.backend.utils.UtilEncriptacion;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepo;

    public Usuario crearUsuario(Usuario usuario) {

        try {
            if (usuarioRepo.existsByEmail(usuario.getEmail())) {
                throw new IllegalArgumentException("Ya existe un usuario con este email.");
            }

            String passwordEncriptado = UtilEncriptacion.encriptar(usuario.getPassword());
            usuario.setPassword(passwordEncriptado);

            return usuarioRepo.save(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    };

    public Usuario actualizarUsuario(Long id, Usuario usuario) {

        Usuario usuarioExiste = usuarioRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado al usuario con id: " + id));

        usuarioExiste.setNombre(usuario.getNombre());
        usuarioExiste.setEmail(usuario.getEmail());

        try {
            String passwordEncriptado = UtilEncriptacion.encriptar(usuario.getPassword());
            usuarioExiste.setPassword(passwordEncriptado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }

        return usuarioRepo.save(usuarioExiste);
    }

    public void eliminarUsuario(Long id) {

        if (!usuarioRepo.existsById(id)) {
            throw new NoSuchElementException("No se ha encontrado al usuario con id: " + id);
        }
        usuarioRepo.deleteById(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> listarUsuarioPorId(Long id) {

        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);

        if (!usuarioOpt.isPresent()) {
            throw new NoSuchElementException("Usuario con id " + id + " no encontrado.");
        }

        usuarioOpt.ifPresent(usuario -> {
            try {
                String passwordDesencriptado = UtilEncriptacion.desencriptar(usuario.getPassword());
                usuario.setPassword(passwordDesencriptado);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al obtener el usuario: " + e.getMessage());
            }
        });

        return usuarioOpt;
    }

    public Optional<Usuario> listarUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new NoSuchElementException("No se encontró ningún usuario con este email: " + email);
        } else {
            return usuarioOpt;
        }
    }

    public List<Usuario> listarUsuarioPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepo.findByNombre(nombre);
        if (usuarios.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún usuario con este nombre: " + nombre);
        } else {
            return usuarios;
        }
    }
}
