package com.fullstack1.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fullstack1.backend.dto.ClienteRequestDTO;
import com.fullstack1.backend.dto.ClienteResponseDTO;
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
    public ResponseEntity<?> crearCliente(@RequestBody ClienteRequestDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(clienteDTO));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long idCliente, @RequestBody ClienteRequestDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.actualizarCliente(idCliente, clienteDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Long idCliente) {
        clienteService.eliminarCliente(idCliente);
    }

    @GetMapping("/listar")
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/id/{idCliente}")
    public ResponseEntity<?> listarClientePorId(@PathVariable Long idCliente) {
            Optional<ClienteResponseDTO> clienteOpt = clienteService.listarClientePorId(idCliente);
            if (clienteOpt.isPresent()) {
                return ResponseEntity.ok(clienteOpt.get());
            } else {
                //ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado cliente con este id", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado cliente con este id");
            }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> listarClientePorEmail(@PathVariable String email) {
        Optional<ClienteResponseDTO> clienteOpt = clienteService.listarClientePorEmail(email);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            //ErrorResponse errorResponse = new ErrorResponse("No se ha econtrado cliente con este id", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha econtrado cliente con este email");
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> listarClientePorNombre(@PathVariable String nombre) {
        List<ClienteResponseDTO> clientes = clienteService.listarClientePorNombre(nombre);
        if (clientes.isEmpty()) {
            //ErrorResponse errorResponse = new ErrorResponse("No se ha encontrado clientes con este nombre", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado clientes con este nombre");
        } else {
            return ResponseEntity.ok(clientes);
        }
    }

}
