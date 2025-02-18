package com.fullstack1.backend.services;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.models.Producto;
import com.fullstack1.backend.repositories.IProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final IProductoRepository productoRepo;

    public Producto crearProducto (Producto producto) {

        try {
            Optional<Producto> productoExiste = productoRepo.findById(producto.getIdProducto());

            if (productoExiste.isPresent()) {
                throw new IllegalArgumentException("Ya existe un producto con este id");                

            } else {
                Producto nuevoProducto = new Producto();
                nuevoProducto.setNombre(producto.getNombre());
                nuevoProducto.setPrecio(producto.getPrecio());

                return productoRepo.save(nuevoProducto);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }
}
