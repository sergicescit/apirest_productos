package com.fullstack1.backend.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.PedidoProducto;

@Repository
public interface IPedidoProductoRepository extends JpaRepository<PedidoProducto, Long> {

    boolean existsByIdPedidoProducto(Long idPedidoProducto);

    Optional<PedidoProducto> findByIdPedidoProducto(Long idPedidoProducto);

    Optional<PedidoProducto> findByPedido_IdPedidoAndProducto_IdProducto(Long idPedido, Long idProducto);

    List<PedidoProducto> findByPedido_IdPedido(Long idPedido);

    List<PedidoProducto> findByProducto_IdProducto(Long idProducto);

    List<PedidoProducto> findByPedido_Cliente_IdCliente(Long idCliente);

    @Query("SELECT pp FROM PedidoProducto pp WHERE pp.cantidad >= :cantidad")
    List<PedidoProducto> findByCantidadIgualMayor(@Param("cantidad") Integer cantidad);

    List<PedidoProducto> findByPedido_FechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT pp FROM PedidoProducto pp " +
            "JOIN pp.pedido p " +
            "JOIN pp.producto pr " +
            "WHERE p.cliente = :idCliente " +
            "AND pr.precio BETWEEN :precioMin AND :precioMax")
    List<PedidoProducto> findByPedido_Cliente_IdCliente_Producto_PrecioEnRango(
            @Param("idCliente") Long idCliente,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax);
}
