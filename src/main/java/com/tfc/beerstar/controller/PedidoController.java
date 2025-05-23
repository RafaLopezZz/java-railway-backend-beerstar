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
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.PedidoService;

@RestController
@RequestMapping("/beerstar/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false, defaultValue = "TARJETA") String metodoPago) {
        PedidoResponseDTO pedidoCreado = pedidoService.crearPedido(userDetails.getId(), metodoPago);
        return ResponseEntity.ok(pedidoCreado);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(userDetails.getId());
        return ResponseEntity.ok(pedidos);
    }
}