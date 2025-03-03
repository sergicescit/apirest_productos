package com.fullstack1.backend.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.dto.PedidoProductoRequestDTO;
import com.fullstack1.backend.dto.PedidoProductoResponseDTO;
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
    public PedidoProducto crearPedidoProducto(PedidoProductoRequestDTO pedidoProductoDTO) {

        boolean yaExiste = pedidoProductoRepo
                .existsByPedido_IdPedidoAndProducto_IdProducto(pedidoProductoDTO.getIdPedido(),
                        pedidoProductoDTO.getIdProducto());
        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un a entrada con estas id");
        }

        Pedido pedido = pedidoRepo.findByIdPedido(pedidoProductoDTO.getIdPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        Producto producto = productoRepo.findByIdProducto(pedidoProductoDTO.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        PedidoProducto pedidoProducto = new PedidoProducto();
        pedidoProducto.setPedido(pedido);
        pedidoProducto.setProducto(producto);
        pedidoProducto.setCantidad(pedidoProductoDTO.getCantidad());

        return pedidoProductoRepo.save(pedidoProducto);
    }

    @Transactional
    public PedidoProductoResponseDTO actualizarPedidoProducto(Long idPedidoProducto,
            PedidoProductoRequestDTO pedidoProductoDTO) {

        PedidoProducto pedidoProductoActual = pedidoProductoRepo.findByIdPedidoProducto(idPedidoProducto)
                .orElseThrow(
                        () -> new EntityNotFoundException("No se ha encontrado ningún Pedido producto con las id"));

        boolean yaExiste = pedidoProductoRepo
                .findByPedido_IdPedidoAndProducto_IdProducto(pedidoProductoDTO.getIdPedido(),
                        pedidoProductoDTO.getIdProducto())
                .filter(pp -> !pp.getIdPedidoProducto().equals(idPedidoProducto))
                .isPresent();

        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un a entrada con estas id");
        }

        Pedido pedidoExiste = pedidoRepo.findByIdPedido(pedidoProductoDTO.getIdPedido())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se ha encontrado ningún pedido con id " + pedidoProductoDTO.getIdPedido()));

        Producto productoExiste = productoRepo.findByIdProducto(pedidoProductoDTO.getIdProducto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se ha encontrado ningún producto con id " + pedidoProductoDTO.getIdProducto()));

        pedidoProductoActual.setPedido(pedidoExiste);
        pedidoProductoActual.setProducto(productoExiste);
        pedidoProductoActual.setCantidad(pedidoProductoDTO.getCantidad());

        PedidoProducto pedidoProductoActualizado = pedidoProductoRepo.save(pedidoProductoActual);

        return new PedidoProductoResponseDTO(pedidoProductoActualizado);

    }

    // Eliminar por IdPedidoProducto directamente
    public void eliminarPedidoProductoPorId(Long idPedidoProducto) {
        if (!pedidoProductoRepo.existsByIdPedidoProducto(idPedidoProducto)) {
            throw new EntityNotFoundException("No se encontró ninún pedidoProducto con id " + idPedidoProducto);
        }

        pedidoProductoRepo.deleteById(idPedidoProducto);
    }

    // Eliminar por los ID de Pedido y Producto
    public void eliminarPedidoProductoPorIds(Long idPedido, Long idProducto) {
        Optional<PedidoProducto> pedidoProducto = pedidoProductoRepo
                .findByPedido_IdPedidoAndProducto_IdProducto(idPedido, idProducto);

        if (pedidoProducto.isPresent()) {
            pedidoProductoRepo.delete(pedidoProducto.get());
        } else {
            throw new EntityNotFoundException("No se ha encontrado ningún Pedido_Producto con este id.");
        }
    }

    public List<PedidoProductoResponseDTO> listarPedidoProducto() {
        List<PedidoProducto> pedidoProductos = pedidoProductoRepo.findAll();
        return pedidoProductos.stream()
                .map(pp -> new PedidoProductoResponseDTO(pp))
                .collect(Collectors.toList());
    }

    public Optional<PedidoProductoResponseDTO> listarPedidoProductoPorId(Long idPedidoProducto) {
        return pedidoProductoRepo.findByIdPedidoProducto(idPedidoProducto)
                .map(PedidoProductoResponseDTO::new);
    }

    public Optional<PedidoProducto> listarPorIdsPedidoYProducto(Long idPedido, Long idProducto) {
        return pedidoProductoRepo.findByPedido_IdPedidoAndProducto_IdProducto(idPedido, idProducto);
    }

    public List<PedidoProductoResponseDTO> listarPedidoProductoPorIdPedido(Long idPedido) {
        return pedidoProductoRepo.findByPedido_IdPedido(idPedido).stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoProductoResponseDTO> listarPedidoProductoPorIdProducto(Long idProducto) {
        return pedidoProductoRepo.findByProducto_IdProducto(idProducto).stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoProductoResponseDTO> listarPedidoProductoPorIdCliente(Long idCliente) {
        return pedidoProductoRepo.findByPedido_Cliente_IdCliente(idCliente).stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoProductoResponseDTO> listarPorCantidadEntre(Integer cantidadMin, Integer cantidadMax) {
        return pedidoProductoRepo.findByCantidadEntre(cantidadMin, cantidadMax).stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoProductoResponseDTO> listarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {

        LocalDateTime fechaInicioDateTime = (fechaInicio != null) ? fechaInicio.atStartOfDay().withNano(0)
                : LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime fechaFinDateTime = (fechaFin != null) ? fechaFin.atTime(23, 59, 59).withNano(0)
                : LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        System.out.println("FECHA INICIO DATE TIME = " + fechaInicioDateTime);
        System.out.println("FECHA FIN DATE TIME = " + fechaFinDateTime);

        return pedidoProductoRepo.findByPedido_Fecha(fechaInicioDateTime, fechaFinDateTime)
                .stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoProductoResponseDTO> listarPorIdClienteYPrecioEnRango(
            Long idCliente, Long idProducto,
            BigDecimal precioMin, BigDecimal precioMax) {
        return pedidoProductoRepo.findByPedido_Cliente_IdCliente_Producto_PrecioEnRango(idCliente, precioMin,
                precioMax).stream()
                .map(PedidoProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

}
