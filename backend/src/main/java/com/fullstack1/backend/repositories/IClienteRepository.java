package com.fullstack1.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Cliente;
import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByIdCliente(Long idCliente);

    boolean existsByEmail(String email);

    Optional<Cliente> findByIdCliente(Long idCliente);

    List<Cliente> findByNombre(String nombre);

    Optional<Cliente> findByEmail(@Param("email") String email);
}
