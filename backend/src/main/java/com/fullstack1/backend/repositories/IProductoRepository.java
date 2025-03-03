package com.fullstack1.backend.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullstack1.backend.models.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
   
    boolean existsByIdProducto(Long idProducto);

    boolean existsByNombre(String nombre);

    Optional<Producto> findByIdProducto(Long idProducto);

    List<Producto> findByNombre(String nombre);

    // Poner atención en que el nombre de la tabla coincida con el de la ENTIDAD, no
    // con el de la tabla en la bbdd
    @Query("SELECT pr FROM Producto pr WHERE pr.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioEnRango(@Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax);
}
