package com.fullstack1.backend.models;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido_producto", uniqueConstraints = {
    @UniqueConstraint (columnNames = {"pedido_id", "producto_id"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_producto")
    private Long idPedidoProducto;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NonNull
    private Integer cantidad;
}
