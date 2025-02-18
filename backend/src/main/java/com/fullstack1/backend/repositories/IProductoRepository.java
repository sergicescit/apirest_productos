package com.fullstack1.backend.repositories;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByNombre(String nombre);
    List<Producto> findByPrecio(DecimalFormat precio);
    Optional<Producto> finbByIdProducto(Long id);
}
