package com.fullstack1.backend.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.fullstack1.backend.dto.PedidoRequestDTO;
import com.fullstack1.backend.dto.PedidoResponseDTO;
import com.fullstack1.backend.services.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

     @PostMapping("/crear")
     public ResponseEntity<?> crearPedido (@RequestBody PedidoRequestDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(pedidoDTO));
     }

     @DeleteMapping("/eliminar/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void eliminarPedido (@PathVariable("id") Long idPedido) {
        pedidoService.eliminarPedido(idPedido);
     }

     @PutMapping("/actualizar/{id}")
     public ResponseEntity<?> actualizarPedido (@PathVariable("id") Long idPedido, @RequestBody PedidoRequestDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.actualizarPedido(idPedido, pedidoDTO));
     }

     @GetMapping("/listar")
     public List<PedidoResponseDTO> listarPedido() {
        return pedidoService.listarPedido();
     }

     @GetMapping("/id/{id}")
     public ResponseEntity<?> listarPedidoPorId (@PathVariable("id") Long idPedido) {
        return ResponseEntity.ok(pedidoService.listarPedidoPorId(idPedido));
     }

     @GetMapping("/pedido-cliente/{id}")
     public ResponseEntity<?> listarPedidoPorIdCliente(@PathVariable("id") Long idCliente) {
        return ResponseEntity.ok(pedidoService.listarPedidoPorIdCliente(idCliente));
     }

     @GetMapping("/fecha")
     public List<PedidoResponseDTO> listarPedidoPorFecha (@RequestParam("fecha") 
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
                                LocalDate fecha){
        return pedidoService.listarPedidoPorFecha(fecha);
    }
}
