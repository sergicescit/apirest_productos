package com.fullstack1.backend.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack1.backend.dto.PedidoProductoRequestDTO;
import com.fullstack1.backend.dto.PedidoProductoResponseDTO;
import com.fullstack1.backend.models.PedidoProducto;
import com.fullstack1.backend.services.PedidoProductoService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido-producto")
public class PedidoProductoController {

    private final PedidoProductoService pedidoProductoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedidoProducto(@RequestBody PedidoProductoRequestDTO dto) {

        PedidoProducto nuevoPedidoProducto = pedidoProductoService.crearPedidoProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedidoProducto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPedidoProducto(@PathVariable Long idPedidoProducto, @RequestBody PedidoProductoRequestDTO dto) {

        PedidoProductoResponseDTO pedidoProductoActualizado = pedidoProductoService.actualizarPedidoProducto(idPedidoProducto, dto);
        return ResponseEntity.ok(pedidoProductoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPedidoProducto(@PathVariable Long idPedidoProducto) {
        pedidoProductoService.eliminarPedidoProductoPorId(idPedidoProducto);
    }

    @GetMapping("/listar")
    public List<PedidoProductoResponseDTO> listarPedidoProducto() {
        return pedidoProductoService.listarPedidoProducto();
    } 

    @GetMapping("/id/{idPedidoProducto}")
    public ResponseEntity<?> listarPedidoProductoPorId (@PathVariable Long idPedidoProducto) {
        Optional<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPedidoProductoPorId(idPedidoProducto);

        if (pedidoProducto.isPresent()) {
            return ResponseEntity.ok(pedidoProducto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el pedido producto con id " + idPedidoProducto);
        }
    }

    @GetMapping("/id-pedido/{idPedido}")
    public ResponseEntity<?> listarPedidoProductoPorIdPedido(@PathVariable Long idPedido) {
        List<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPedidoProductoPorIdPedido(idPedido);
    
        if (pedidoProducto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un pedido producto con id de pedido " + idPedido);
        } else {
            return ResponseEntity.ok(pedidoProducto);
        }
    }

    @GetMapping("/id-producto/{idProducto}")
    public ResponseEntity<?> listarPedidoProductoPorIdProducto(@PathVariable Long idProducto) {
        List<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPedidoProductoPorIdProducto(idProducto);

        if (pedidoProducto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un pedido producto con id de producto " + idProducto);
        } else {
            return ResponseEntity.ok(pedidoProducto);
        }
    }

    @GetMapping("/id-cliente/{idCliente}")
    public ResponseEntity<?> listarPedidoProductoPorIdCliente( @PathVariable Long idCliente) {
        List<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPedidoProductoPorIdCliente(idCliente);

        if (pedidoProducto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado un pedido producto con id de cliente " + idCliente);
        } else {
            return ResponseEntity.ok(pedidoProducto);
        }
    }

    @GetMapping("/cantidad")
    public ResponseEntity<?> listarPorCantidadEntre( @RequestParam(required = false) Integer cantidadMin,
                                                            @RequestParam(required = false) Integer cantidadMax) {
        List<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPorCantidadEntre(cantidadMin, cantidadMax);

        if (pedidoProducto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado pedido producto por la cantidad solicitada");
        } else {
            return ResponseEntity.ok(pedidoProducto);
        }
    }

    @GetMapping("/fecha")
    public ResponseEntity<?> listarPorIntervaloFechas( @RequestParam( required = false) LocalDate fechaInicio, 
                                                        @RequestParam( required = false) LocalDate fechaFin) {
        List<PedidoProductoResponseDTO> pedidoProducto = pedidoProductoService.listarPorFecha(fechaInicio, fechaFin);
        
        if (pedidoProducto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado pedido producto con esas fechas");
        } else {
            return ResponseEntity.ok(pedidoProducto);
        }
    }


}
