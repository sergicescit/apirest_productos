package com.fullstack1.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack1.backend.dto.PedidoProductoRequestDTO;
import com.fullstack1.backend.models.PedidoProducto;
import com.fullstack1.backend.services.PedidoProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido-producto")
public class PedidoProductoController {

    private final PedidoProductoService pedidoProductoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedidoProducto(@RequestBody @Valid PedidoProductoRequestDTO dto) {

        PedidoProducto nuevoPedidoProducto = pedidoProductoService.crearPedidoProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedidoProducto);
    }

    @GetMapping("/listar")
    public List<PedidoProducto> listarPedidoProducto() {
        return pedidoProductoService.listarPedidoProducto();
    }
}
