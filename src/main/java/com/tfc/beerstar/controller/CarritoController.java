package com.tfc.beerstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.AddToCarritoRequestDTO;
import com.tfc.beerstar.dto.response.CarritoResponseDTO;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.CarritoService;
import com.tfc.beerstar.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/beerstar/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<CarritoResponseDTO> agregarAlCarrito(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AddToCarritoRequestDTO request) {
        Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        CarritoResponseDTO response = carritoService.agregarACarrito(cliente, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public CarritoResponseDTO verCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        return carritoService.verCarrito(cliente);
    }

    @PostMapping("/{idArticulo}")
    public ResponseEntity<CarritoResponseDTO> decrementarArticulo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long idArticulo) {
        Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        CarritoResponseDTO response = carritoService.decrementarArticulo(cliente, idArticulo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> vaciarCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Cliente cliente = clienteService.findByEmail(userDetails.getUsername());
        carritoService.vaciarCarrito(cliente);
        return ResponseEntity.noContent().build();
    }

}
