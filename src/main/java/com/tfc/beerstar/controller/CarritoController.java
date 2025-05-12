package com.tfc.beerstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.AddToCarritoRequestDTO;
import com.tfc.beerstar.dto.response.CarritoResponseDTO;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.service.CarritoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/beerstar/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CLIENTE')")
    public CarritoResponseDTO agregarACarrito(@AuthenticationPrincipal Cliente cliente, @Valid @RequestBody AddToCarritoRequestDTO request) {
        return carritoService.agregarACarrito(cliente, request);
    }

    @GetMapping("/verCarrito")
    @PreAuthorize("hasRole('CLIENTE')")
    public CarritoResponseDTO verCarrito(@AuthenticationPrincipal Cliente cliente) {
        return carritoService.verCarrito(cliente);
    }

}
