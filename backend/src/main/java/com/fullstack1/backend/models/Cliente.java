package com.fullstack1.backend.models;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente", uniqueConstraints = {
@UniqueConstraint(columnNames = "email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_cliente", nullable = false)
        private Long idCliente;

        @NonNull
        private String nombre;

        @NonNull
        private String email;

        @NonNull
        private String password;

        @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Pedido> pedidos = new ArrayList<>();
}
