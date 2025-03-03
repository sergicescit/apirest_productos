package com.fullstack1.backend.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.dto.ProductoRequestDTO;
import com.fullstack1.backend.dto.ProductoResponseDTO;
import com.fullstack1.backend.models.Producto;
import com.fullstack1.backend.repositories.IProductoRepository;
import com.fullstack1.backend.utilidadesGenerales.UtilidadesGenerales;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final IProductoRepository productoRepo;

    public Producto crearProducto(ProductoRequestDTO productoDTO) {
        
        boolean productoExiste = productoRepo.existsByNombre(productoDTO.getNombre());

        if (productoExiste) {
            throw new IllegalArgumentException("Ya existe un producto con este nombre");
        }

        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());

        return productoRepo.save(producto);
    }

    public void eliminarProducto(Long idProducto) {

        if (!productoRepo.existsByIdProducto(idProducto)) {
            throw new IllegalArgumentException("No se encontró ningun producto con este id");
        }
        productoRepo.deleteById(idProducto);
    }

    public Producto actualizarProducto(Long idProducto, ProductoRequestDTO productoDTO) {

        Producto productoExiste = productoRepo.findById(idProducto)
                .orElseThrow(
                        () -> new NoSuchElementException("No se ha encontrado ningún producto con id: " + idProducto));

        productoExiste.setNombre(productoDTO.getNombre());
        productoExiste.setPrecio(productoDTO.getPrecio());

        return productoRepo.save(productoExiste);
    }

    public List<ProductoResponseDTO> listarProducto() {
        List<Producto> productos =  productoRepo.findAll();

        return productos.stream()
                .map(ProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO listarProductoPorIdProducto(Long idProducto) {

        Producto producto = productoRepo.findByIdProducto(idProducto)
                .orElseThrow(() -> new NoSuchElementException("No se encontro ningún producto con id " + idProducto));
        
        return new ProductoResponseDTO(producto);
    }

    public List<ProductoResponseDTO> listarProductoPorNombre(String nombre) {

        List<Producto> productos = productoRepo.findByNombre(nombre);
        if (productos.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún producto con nombre: " + nombre);
        } else {
            return productos.stream()
                    .map(ProductoResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public List<ProductoResponseDTO> listarProductosPorPrecio(BigDecimal precioMin, BigDecimal precioMax) {

        if (UtilidadesGenerales.valoresDePrecioValidos(precioMin, precioMax)) {
            return productoRepo.findByPrecioEnRango(precioMin, precioMax).stream()
                    .map(ProductoResponseDTO::new)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "Los valores no pueden ser igual a cero ni el importe mimimo mayor al máximo.");
        }
    }
}
