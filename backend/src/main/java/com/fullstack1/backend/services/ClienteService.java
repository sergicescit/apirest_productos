package com.fullstack1.backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fullstack1.backend.dto.ClienteRequestDTO;
import com.fullstack1.backend.dto.ClienteResponseDTO;
import com.fullstack1.backend.models.Cliente;
import com.fullstack1.backend.repositories.IClienteRepository;

import lombok.RequiredArgsConstructor;
import com.fullstack1.backend.utils.UtilEncriptacion;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final IClienteRepository clienteRepo;

    public Cliente crearCliente(ClienteRequestDTO clienteDTO) {

        if (clienteRepo.existsByEmail(clienteDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un cliente con este email " + clienteDTO.getEmail());
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());

        try {
            cliente.setPassword(UtilEncriptacion.encriptar(clienteDTO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
        }

        return clienteRepo.save(cliente);
    };

    public Cliente actualizarCliente(Long id, ClienteRequestDTO clienteDTO) {

        Cliente clienteExiste = clienteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha encontrado al cliente con id: " + id));

        clienteExiste.setNombre(clienteDTO.getNombre());
        clienteExiste.setEmail(clienteDTO.getEmail());

        try {
            clienteExiste.setPassword(UtilEncriptacion.encriptar(clienteDTO.getPassword()));
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

    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepo.findAll().stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ClienteResponseDTO> listarClientePorId(Long idCliente) {
        Optional<Cliente> clienteOpt = clienteRepo.findById(idCliente);

        if (!clienteOpt.isPresent()) {
            throw new NoSuchElementException("Cliente con id " + idCliente + " no encontrado.");
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

        return clienteOpt.map(ClienteResponseDTO::new);
    }

    public Optional<ClienteResponseDTO> listarClientePorEmail(String email) {
        Optional<Cliente> clienteOpt = clienteRepo.findByEmail(email);

        if (!clienteOpt.isPresent()) {
            throw new NoSuchElementException("No se encontró ningún cliente con este email: " + email);
        } else {
            return clienteOpt.map(ClienteResponseDTO::new);
        }
    }

    public List<ClienteResponseDTO> listarClientePorNombre(String nombre) {
        List<Cliente> clientes = clienteRepo.findByNombre(nombre);

        if (clientes.isEmpty()) {
            throw new NoSuchElementException("No se encontró ningún cliente con este nombre: " + nombre);
        } else {
            return clientes.stream()
                    .map(ClienteResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
