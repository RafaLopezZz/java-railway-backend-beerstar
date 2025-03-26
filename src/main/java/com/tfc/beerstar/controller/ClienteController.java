package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ClienteRequestDTO;
import com.tfc.beerstar.dto.response.ClienteResponseDTO;
import com.tfc.beerstar.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/beerstar/usuarios/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/registroCliente")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO response = clienteService.crearCliente(clienteRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable("id") Long id) {
        ClienteResponseDTO response = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarClientes")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> lista = clienteService.listarClientes();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/actualizarCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable("id") Long id,
                                                                  @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO response = clienteService.actualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarCliente/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
