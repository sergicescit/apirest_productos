package com.fullstack1.backend.controllers;

import java.time.LocalDateTime;
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

import com.fullstack1.backend.models.Pedido;
import com.fullstack1.backend.services.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

     @PostMapping("/crear")
     public ResponseEntity<?> crearPedido (@RequestBody Pedido pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(pedido));
     }

     @DeleteMapping("/eliminar/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void eliminarPedido (@PathVariable Long idPedido) {
        pedidoService.eliminarPedido(idPedido);
     }

     @PutMapping("/actualizar/{id}")
     public ResponseEntity<?> actualizarPedido (@PathVariable Long idPedido, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.actualizarPedido(idPedido, pedido));
     }

     @GetMapping("/listar")
     public List<Pedido> listarPedido() {
        return pedidoService.listarPedido();
     }

     @GetMapping("/id/{id}")
     public ResponseEntity<?> listarPedidoPorId (@PathVariable Long idPedido) {
        return ResponseEntity.ok(pedidoService.listarPedidoPorId(idPedido));
     }

     @GetMapping("/pedido-cliente/{id}")
     public ResponseEntity<?> listarPedidoPorIdCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.listarPedidoPorIdCliente(idCliente));
     }

     @GetMapping("/fecha")
     public List<Pedido> listarPedidoPorFecha (@RequestParam("fecha") 
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
                                LocalDateTime fecha){
        return pedidoService.listarPedidoPorFecha(fecha);
    }
}
