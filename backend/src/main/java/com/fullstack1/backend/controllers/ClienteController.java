package com.fullstack1.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fullstack1.backend.models.Cliente;
import com.fullstack1.backend.services.ClienteService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(cliente));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente));
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> listarClientePorId(@PathVariable Long id) {
            Optional<Cliente> clienteOpt = clienteService.listarClientePorId(id);
            if (clienteOpt.isPresent()) {
                return ResponseEntity.ok(clienteOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado cliente con este id");
            }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> listarClientePorEmail(@PathVariable String email) {
        Optional<Cliente> clienteOpt = clienteService.listarClientePorEmail(email);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado cliente con este email");
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> listarClientePorNombre(@PathVariable String nombre) {
        List<Cliente> clientes = clienteService.listarClientePorNombre(nombre);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado clientes con este nombre");
        } else {
            return ResponseEntity.ok(clientes);
        }
    }

}
