package com.fullstack1.backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fullstack1.backend.dto.UsuarioRequestDTO;
import com.fullstack1.backend.dto.UsuarioResponseDTO;
import com.fullstack1.backend.models.Usuario;
import com.fullstack1.backend.repositories.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import com.fullstack1.backend.utils.UtilEncriptacion;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepo;

    public Usuario crearUsuario(UsuarioRequestDTO usuarioDTO) {

        try {
            if (usuarioRepo.existsByEmail(usuarioDTO.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un usuario con este email " + usuarioDTO.getEmail());
            }

            String passwordEncriptado = UtilEncriptacion.encriptar(usuarioDTO.getPassword());
            
            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setPassword(passwordEncriptado);

            return usuarioRepo.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    };

    public Usuario actualizarUsuario(Long idUsuario, UsuarioRequestDTO usuarioDTO) {

        Usuario usuarioExiste = usuarioRepo.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado al usuario con id: " + idUsuario));

        usuarioExiste.setNombre(usuarioDTO.getNombre());
        usuarioExiste.setEmail(usuarioDTO.getEmail());

        try {
            String passwordEncriptado = UtilEncriptacion.encriptar(usuarioDTO.getPassword());
            usuarioExiste.setPassword(passwordEncriptado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }

        return usuarioRepo.save(usuarioExiste);
    }

    public void eliminarUsuario(Long idUsuario) {

        if (!usuarioRepo.existsById(idUsuario)) {
            throw new NoSuchElementException("No se ha encontrado al usuario con id: " + idUsuario);
        }
        usuarioRepo.deleteById(idUsuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {

        List<Usuario> usuarios = usuarioRepo.findAll();

        return usuarios.stream()   
                    .map(usuario -> new UsuarioResponseDTO(
                        usuario.getIdUsuario(),
                        usuario.getNombre(),
                        usuario.getEmail()
                    ))
                    .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> listarUsuarioPorId(Long idUsuario) {

        Optional<Usuario> usuarioOpt = usuarioRepo.findByIdUsuario(idUsuario);

        if (!usuarioOpt.isPresent()) {
            throw new NoSuchElementException("Usuario con id " + idUsuario + " no encontrado.");
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

        return usuarioOpt.map(UsuarioResponseDTO::new);
    }

    public Optional<UsuarioResponseDTO> listarUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new NoSuchElementException("No se encontró ningún usuario con este email: " + email);
        } else {
            return usuarioOpt.map(UsuarioResponseDTO::new);
        }
    }

    public List<UsuarioResponseDTO> listarUsuarioPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepo.findByNombre(nombre);
        if (usuarios.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún usuario con este nombre: " + nombre);
        } else {
            return usuarios.stream()
                    .map(UsuarioResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
