package com.fullstack1.backend.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.dto.PedidoRequestDTO;
import com.fullstack1.backend.dto.PedidoResponseDTO;
import com.fullstack1.backend.models.Cliente;
import com.fullstack1.backend.models.Pedido;
import com.fullstack1.backend.repositories.IClienteRepository;
import com.fullstack1.backend.repositories.IPedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final IPedidoRepository pedidoRepo;
    private final IClienteRepository clienteRepo;

    public Pedido crearPedido (PedidoRequestDTO pedidoDTO) {

        Cliente cliente = clienteRepo.findByIdCliente(pedidoDTO.getClienteId())
                            .orElseThrow (() -> new EntityNotFoundException("No se encontró ningún cliente con id " + pedidoDTO.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDateTime.now());

        return pedidoRepo.save(pedido);
    }

    public void eliminarPedido (Long id) {

        if (!pedidoRepo.existsByIdPedido(id)) {
            throw new IllegalArgumentException("No se ha encontrado el pedido con id " + id);
        }
        pedidoRepo.deleteById(id);
    }

    public PedidoResponseDTO actualizarPedido (Long idPedido, PedidoRequestDTO pedidoDTO) {

        Cliente cliente = clienteRepo.findById(pedidoDTO.getClienteId())
                            .orElseThrow (() -> new EntityNotFoundException("No se econtró ninún cliente con id " + pedidoDTO.getClienteId()));
        
        Pedido pedidoExiste = pedidoRepo.findByIdPedido(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningún pedido con id " + idPedido));
        
        pedidoExiste.setCliente(cliente);
        
        Pedido pedidoActualizado = pedidoRepo.save(pedidoExiste);
        return new PedidoResponseDTO(pedidoActualizado);
    }

    public List<PedidoResponseDTO> listarPedido() {
        return pedidoRepo.findAll().stream()
            .map(PedidoResponseDTO::new)
            .collect(Collectors.toList());
    }

    public PedidoResponseDTO listarPedidoPorId (Long idPedido) {
        return pedidoRepo.findByIdPedido(idPedido)
                    .map(PedidoResponseDTO::new)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún pedido con esta id " + idPedido));  
    }

    public List<PedidoResponseDTO> listarPedidoPorIdCliente (Long idCliente) {  
        List<Pedido> pedidos = pedidoRepo.findByCliente_IdCliente(idCliente);
        if (pedidos.isEmpty()) {
            throw new EntityNotFoundException("No se ha encontrado ningún cliente con id " +idCliente);
        } else {
            return pedidos.stream()
                    .map(PedidoResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public List<PedidoResponseDTO> listarPedidoPorFecha (LocalDate fecha) {

        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.atTime(23, 59, 59);

        List<Pedido> pedidos = pedidoRepo.findByFecha(startOfDay, endOfDay);

        if (pedidos.isEmpty()) {
            throw new EntityNotFoundException("No se ha encontrado ningún pedido por fecha " + fecha);
        }
        
        return pedidos.stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
