package com.tfc.beerstar.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.PagoRequestDTO;
import com.tfc.beerstar.dto.response.PagoResponseDTO;
import com.tfc.beerstar.service.PagoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping("/generarPago")
    @PreAuthorize("hasRole('CLIENTE')")
    public PagoResponseDTO generarPago(@Valid @RequestBody PagoRequestDTO req) {
        return service.registrar(req);
    }
}
