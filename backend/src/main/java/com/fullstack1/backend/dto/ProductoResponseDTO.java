package com.fullstack1.backend.dto;

import java.math.BigDecimal;

import com.fullstack1.backend.models.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoResponseDTO {

    private Long idProducto;
    private String nombre;
    private BigDecimal precio;

    public ProductoResponseDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
    }
}
