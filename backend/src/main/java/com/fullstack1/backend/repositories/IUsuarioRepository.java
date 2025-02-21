package com.fullstack1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Usuario;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByIdUser(Long idUser);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM Usuario u WHERE u.idUser = :idUser")
    Optional<Usuario> findByIdUser(@Param("idUser") Long idUser);
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    List<Usuario> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
}
