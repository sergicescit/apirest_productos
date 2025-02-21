package com.fullstack1.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.models.Pedido;
import com.fullstack1.backend.repositories.IPedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final IPedidoRepository pedidoRepo;

    public Pedido crearPedido (Pedido pedido) {
        
        if (pedidoRepo.existsByIdPedido(pedido.getIdPedido())) {
            throw new NoSuchElementException("Ya existe un pedido con este id");
        }
        return pedidoRepo.save(pedido);
    }

    public void eliminarPedido (Long id) {

        if (!pedidoRepo.existsByIdPedido(id)) {
            throw new IllegalArgumentException("No se ha encontrado el pedido con id " + id);
        }
        pedidoRepo.deleteById(id);
    }

    public Pedido actualizarPedido (Long idPedido, Pedido pedido) {
        Pedido pedidoExiste = pedidoRepo.findByIdPedido(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningún pedido con id " + idPedido));
        
        pedidoExiste.setCliente(pedido.getCliente());
        pedidoExiste.setFecha(pedido.getFecha());

        return pedidoRepo.save(pedidoExiste);
    }

    public List<Pedido> listarPedido() {
        return pedidoRepo.findAll();
    }

    public Pedido listarPedidoPorId (Long idPedido) {
        return pedidoRepo.findByIdPedido(idPedido)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún pedido con esta id " + idPedido));  
    }

    public List<Pedido> listarPedidoPorIdCliente (Long idCliente) {  
        List<Pedido> pedidos = pedidoRepo.findByCliente_IdCliente(idCliente);
        if (pedidos.isEmpty()) {
            throw new EntityNotFoundException("No se ha encontrado ningún cliente con id " +idCliente);
        } else {
            return pedidos;
        }
    }

    public List<Pedido> listarPedidoPorFecha (LocalDateTime fecha) {
        List<Pedido> pedidos = pedidoRepo.findByFecha(fecha);
        if (pedidos.isEmpty()) {
            throw new EntityNotFoundException("No se ha encontrado ningún pedido por fecha " + fecha);
        } else {
            return pedidos;
        }
    }

}
