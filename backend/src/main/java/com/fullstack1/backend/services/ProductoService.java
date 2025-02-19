package com.fullstack1.backend.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.models.Producto;
import com.fullstack1.backend.repositories.IProductoRepository;
import com.fullstack1.backend.utilidadesGenerales.UtilidadesGenerales;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final IProductoRepository productoRepo;

    public Producto crearProducto(Producto producto) {

        if (productoRepo.existsByIdProducto(producto.getIdProducto())) {
            throw new IllegalArgumentException("Ya existe un producto con este id");
        }
        return productoRepo.save(producto);
    }

    public void eliminarProducto(Long idProducto) {

        if (!productoRepo.existsByIdProducto(idProducto)) {
            throw new IllegalArgumentException("No se encontró ningun producto con este id");
        }
        productoRepo.deleteById(idProducto);
    }

    public Producto actualizarProducto(Long idProducto, Producto producto) {

        Producto productoExiste = productoRepo.findById(idProducto)
                .orElseThrow(
                        () -> new NoSuchElementException("No se ha encontrado ningún producto con id: " + idProducto));

        productoExiste.setNombre(producto.getNombre());
        productoExiste.setPrecio(producto.getPrecio());

        return productoRepo.save(productoExiste);
    }

    public List<Producto> listarProducto() {
        return productoRepo.findAll();
    }

    public Producto listarProductoPorIdProducto(Long idProducto) {

        return productoRepo.findByIdProducto(idProducto)
                .orElseThrow(() -> new NoSuchElementException("No se encontro ningún producto con id " + idProducto));
    }

    public List<Producto> listarProductoPorNombre(String nombre) {

        List<Producto> productos = productoRepo.findByNombre(nombre);
        if (productos.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún producto con nombre: " + nombre);
        } else {
            return productos;
        }
    }

    public List<Producto> listarProductosPorPrecio(BigDecimal precioMin, BigDecimal precioMax) {

        if (UtilidadesGenerales.valoresDePrecioValidos(precioMin, precioMax)) {
            return productoRepo.findByPrecioEnRango(precioMin, precioMax);
        } else {
            throw new IllegalArgumentException(
                    "Los valores no pueden ser igual a cero ni el importe mimimo mayor al máximo.");
        }
    }
}
