package com.fullstack1.backend.controllers;

import java.math.BigDecimal;
import java.util.List;

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

import com.fullstack1.backend.models.Producto;
import com.fullstack1.backend.services.ProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(producto));
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(idProducto, producto));
    }

    @GetMapping("/listar")
    public List<Producto> listarProducto() {
        return productoService.listarProducto();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> listarProductoPorIdProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.listarProductoPorIdProducto(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> listarProductoPorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.listarProductoPorNombre(nombre);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron productos con el nombre " + nombre);
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/precio")
    public ResponseEntity<?> listarProductosPorPrecio(
            @RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax) {

        List<Producto> productos = productoService.listarProductosPorPrecio(precioMin, precioMax);

        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron productos en ese rango de precios");
        } else {
            return ResponseEntity.ok(productos);
        }
    }

}
