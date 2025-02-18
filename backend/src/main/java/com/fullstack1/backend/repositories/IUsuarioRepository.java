package com.fullstack1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Usuario;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByIdUser(Long idUser);
    Optional<Usuario> findByIdUser(Long idUser);
    List<Usuario> findByNombre(String nombre);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
