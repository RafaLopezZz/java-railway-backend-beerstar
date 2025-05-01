package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.response.PedidoResponseDTO;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.service.PedidoService;

@RestController
@RequestMapping("/beerstar/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crearPedido")
    @PreAuthorize("hasRole('CLIENTE')")
    public PedidoResponseDTO crear(@AuthenticationPrincipal Cliente cliente) {
        return pedidoService.crearPedido(cliente);
    }

    @GetMapping("/listarPedidos")
    @PreAuthorize("hasAnyRole('CLIENTE','PROVEEDOR','ADMIN')")
    public List<PedidoResponseDTO> listar(@AuthenticationPrincipal Cliente cliente) {
        return pedidoService.listarPorCliente(cliente);
    }
}
