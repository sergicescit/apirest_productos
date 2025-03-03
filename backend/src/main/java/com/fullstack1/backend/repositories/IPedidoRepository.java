package com.fullstack1.backend.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Pedido;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByIdPedido(Long idPedido);

    Optional<Pedido> findByIdPedido(Long idPedido);

    List<Pedido> findByCliente_IdCliente(Long clienteId);

    // Alternativa compatible con todas bbdd
    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN :startOfDay AND :endOfDay")
    List<Pedido> findByFecha(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
