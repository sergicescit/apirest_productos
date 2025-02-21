package com.fullstack1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Cliente;
import java.util.List;
import java.util.Optional;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
    boolean existsByIdCliente(Long idCliente);
    boolean existsByEmail(String email);
    @Query("SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    Optional<Cliente> findByIdCliente(@Param("idCliente") Long idCliente);
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    List<Cliente> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    Optional<Cliente> findByEmail(@Param("email") String email);
}
