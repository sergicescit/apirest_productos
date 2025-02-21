package com.fullstack1.backend.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.dto.PedidoProductoRequestDTO;
import com.fullstack1.backend.models.Pedido;
import com.fullstack1.backend.models.PedidoProducto;
import com.fullstack1.backend.models.Producto;
import com.fullstack1.backend.repositories.IPedidoProductoRepository;
import com.fullstack1.backend.repositories.IPedidoRepository;
import com.fullstack1.backend.repositories.IProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoProductoService {

    private final IPedidoProductoRepository pedidoProductoRepo;
    private IPedidoRepository pedidoRepo;
    private IProductoRepository productoRepo;

    @Transactional
    public PedidoProducto crearPedidoProducto(PedidoProductoRequestDTO dto) {

        Pedido pedido = pedidoRepo.findByIdPedido(dto.getIdPedido())
                .orElseThrow (() -> new EntityNotFoundException("Pedido no encontrado"));

        Producto producto = productoRepo.findByIdProducto(dto.getIdProducto())
                .orElseThrow (() -> new EntityNotFoundException("Producto no encontrado"));
        
        boolean yaExiste = pedidoProductoRepo.findByPedido_IdPedidoAndProducto_IdProducto(dto.getIdPedido(), dto.getIdProducto()).isPresent();
        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un a entrada con estas id");
        }

        PedidoProducto pedidoProducto = new PedidoProducto();
        pedidoProducto.setPedido(pedido);
        pedidoProducto.setProducto(producto);
        pedidoProducto.setCantidad(dto.getCantidad());

        return pedidoProductoRepo.save(pedidoProducto);
    }

    // Eliminar por IdPedidoProducto directamente
    public void eliminarPedidoProductoPorId(Long idPedidoProducto) {
        pedidoProductoRepo.deleteById(idPedidoProducto);
    }

    // Eliminar por los ID de Pedido y Producto
    public void eliminarPedidoProductoPorIds(Long idPedido, Long idProducto) {
        Optional<PedidoProducto> pedidoProducto = pedidoProductoRepo.findByPedido_IdPedidoAndProducto_IdProducto(idPedido, idProducto);

        if (pedidoProducto.isPresent()) {
            pedidoProductoRepo.delete(pedidoProducto.get());
        } else {
            throw new EntityNotFoundException("No se ha encontrado ningún Pedido_Producto con este id.");
        }
    }

    public List<PedidoProducto> listarPedidoProducto() {
        return pedidoProductoRepo.findAll();
    }

    public Optional<PedidoProducto> listarPedidoProductoPorId(Long idPedidoProducto) {
        return pedidoProductoRepo.findByIdPedidoProducto(idPedidoProducto);
    }

    public Optional<PedidoProducto> listarPorIdsPedidoYProducto(Long idPedido, Long idProducto) {
        return pedidoProductoRepo.findByPedido_IdPedidoAndProducto_IdProducto(idPedido, idProducto);
    }

    public List<PedidoProducto> listarPedidoProductoPorIdPedido(Long idPedido) {
        return pedidoProductoRepo.findByPedido_IdPedido(idPedido);
    }

    public List<PedidoProducto> listarPedidoProductoPorIdProducto(Long idProducto) {
        return pedidoProductoRepo.findByProducto_IdProducto(idProducto);
    }

    public List<PedidoProducto> listarPedidoProductoPorIdCliente(Long idCliente) {
        return pedidoProductoRepo.findByPedido_Cliente_IdCliente(idCliente);
    }

    public List<PedidoProducto> listarPorCantidadIgualOMayor(Integer cantidad) {
        return pedidoProductoRepo.findByCantidadIgualMayor(cantidad);
    }

    public List<PedidoProducto> listarPorIntervaloFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoProductoRepo.findByPedido_FechaBetween(fechaInicio, fechaFin);
    }

    public List<PedidoProducto> listarPorIdClienteYPrecioEnRango(
            Long idCliente, Long idProducto,
            BigDecimal precioMin, BigDecimal precioMax) {
        return pedidoProductoRepo.findByPedido_Cliente_IdCliente_Producto_PrecioEnRango(idCliente, precioMin, precioMax);
    }

}
