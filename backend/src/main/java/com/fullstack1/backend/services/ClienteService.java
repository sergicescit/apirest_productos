package com.fullstack1.backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fullstack1.backend.models.Cliente;
import com.fullstack1.backend.repositories.IClienteRepository;

import lombok.RequiredArgsConstructor;
import com.fullstack1.backend.utils.UtilEncriptacion;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final IClienteRepository clienteRepo;

    public Cliente crearCliente(Cliente cliente) {

        try {
            if (clienteRepo.existsByEmail(cliente.getEmail())) {
                throw new IllegalArgumentException("Ya existe un cliente con este email.");
            }

            String passwordEncriptado = UtilEncriptacion.encriptar(cliente.getPassword());
            cliente.setPassword(passwordEncriptado);

            return clienteRepo.save(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
        }
    };

    public Cliente actualizarCliente(Long id, Cliente cliente) {

        Cliente clienteExiste = clienteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado al cliente con id: " + id));

        clienteExiste.setNombre(cliente.getNombre());
        clienteExiste.setEmail(cliente.getEmail());

        try {
            String passwordEncriptado = UtilEncriptacion.encriptar(cliente.getPassword());
            clienteExiste.setPassword(passwordEncriptado);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el cliente: " + e.getMessage());
        }

        return clienteRepo.save(clienteExiste);
    }

    public void eliminarCliente(Long id) {

        if (!clienteRepo.existsById(id)) {
            throw new NoSuchElementException("No se ha encontrado al cliente con id: " + id);
        }
        clienteRepo.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    public Optional<Cliente> listarClientePorId(Long id) {

        Optional<Cliente> clienteOpt = clienteRepo.findById(id);

        if (!clienteOpt.isPresent()) {
            throw new NoSuchElementException("Cliente con id " + id + " no encontrado.");
        }

        clienteOpt.ifPresent(cliente -> {
            try {
                String passwordDesencriptado = UtilEncriptacion.desencriptar(cliente.getPassword());
                cliente.setPassword(passwordDesencriptado);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al obtener el cliente: " + e.getMessage());
            }
        });

        return clienteOpt;
    }

    public Optional<Cliente> listarClientePorEmail(String email) {
        Optional<Cliente> clienteOpt = clienteRepo.findByEmail(email);
        if (!clienteOpt.isPresent()) {
            throw new NoSuchElementException("No se encontró ningún cliente con este email: " + email);
        } else {
            return clienteOpt;
        }
    }

    public List<Cliente> listarClientePorNombre(String nombre) {
        List<Cliente> clientes = clienteRepo.findByNombre(nombre);
        if (clientes.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún cliente con este nombre: " + nombre);
        } else {
            return clientes;
        }
    }
}

