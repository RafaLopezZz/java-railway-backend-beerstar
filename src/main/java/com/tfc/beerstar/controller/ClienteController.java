package com.tfc.beerstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ClienteRequestDTO;
import com.tfc.beerstar.dto.response.ClienteResponseDTO;
import com.tfc.beerstar.service.ClienteService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/beerstar/usuarios/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorUsuarioId(@PathVariable Long usuarioId) {
        ClienteResponseDTO cliente = clienteService.obtenerClientePorUsuarioId(usuarioId);
        return ResponseEntity.ok(cliente);
    }
    
    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long clienteId,
            @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(clienteId, clienteRequestDTO);
        return ResponseEntity.ok(clienteActualizado);
    }
}
