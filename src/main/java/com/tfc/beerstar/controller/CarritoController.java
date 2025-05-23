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
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/beerstar/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Operaciones sobre el carrito de compras")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Añadir artículo al carrito",
               description = "Valida stock, crea o recupera el carrito activo y retorna el estado actualizado")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Carrito devuelto correctamente"),
      @ApiResponse(responseCode = "400", description = "Cantidad inválida"),
      @ApiResponse(responseCode = "404", description = "Artículo no encontrado"),
      @ApiResponse(responseCode = "409", description = "Stock insuficiente")
    })
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> agregarAlCarrito(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AddToCarritoRequestDTO request) {
        CarritoResponseDTO response = carritoService.agregarACarrito(userDetails.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public CarritoResponseDTO verCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return carritoService.verCarrito(userDetails.getId());
    }

    @PostMapping("/{idArticulo}")
    public ResponseEntity<CarritoResponseDTO> decrementarArticulo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long idArticulo) {
        CarritoResponseDTO response = carritoService.decrementarArticulo(userDetails.getId(), idArticulo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> vaciarCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        carritoService.vaciarCarrito(userDetails.getId());
        return ResponseEntity.noContent().build();
    }

}
