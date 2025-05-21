package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.response.PedidoResponseDTO;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.ClienteService;
import com.tfc.beerstar.service.PedidoService;

@RestController
@RequestMapping("/beerstar/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

     @Autowired
    private ClienteService clienteService;

    private final PedidoService pedidoService;
    
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false, defaultValue = "TARJETA") String metodoPago) {
            Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        PedidoResponseDTO pedidoCreado = pedidoService.crearPedido(cliente, metodoPago);
        return ResponseEntity.ok(pedidoCreado);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(cliente);
        return ResponseEntity.ok(pedidos);
    }
}