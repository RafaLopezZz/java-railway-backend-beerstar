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
import com.tfc.beerstar.dto.response.MessageResponse;
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar el carrito de compras en Beerstar.
 *
 * <p>
 * Este controlador proporciona operaciones completas para la gestión del
 * carrito de compras de usuarios autenticados, incluyendo agregar artículos,
 * visualizar contenido, decrementar cantidades y vaciar el carrito.</p>
 *
 * <p>
 * Endpoints disponibles:</p>
 * <ul>
 * <li>POST /rlp/carrito → Agregar artículo al carrito</li>
 * <li>GET /rlp/carrito → Ver contenido del carrito</li>
 * <li>POST /rlp/carrito/{id} → Decrementar cantidad de artículo</li>
 * <li>DELETE /rlp/carrito → Vaciar carrito completo</li>
 * </ul>
 *
 * <p>
 * <strong>Nota:</strong> Todos los endpoints requieren autenticación JWT
 * válida.</p>
 *
 * @author rafalopezzz
 */
@RestController
@RequestMapping("/rlp/carrito")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Operaciones sobre el carrito de compras")
public class CarritoController {

    /**
     * Agrega un artículo al carrito de compras del usuario autenticado.
     *
     * <p>
     * Este endpoint valida la disponibilidad de stock, crea un nuevo carrito si
     * no existe uno activo para el usuario, o actualiza el carrito existente
     * agregando la cantidad especificada del artículo.</p>
     *
     * <p>
     * Validaciones realizadas:</p>
     * <ul>
     * <li>Existencia del artículo en el catálogo</li>
     * <li>Disponibilidad de stock suficiente</li>
     * <li>Cantidad válida (mayor a 0)</li>
     * </ul>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado
     * automáticamente)
     * @param request DTO con el ID del artículo y cantidad a agregar
     * @return ResponseEntity con el estado actualizado del carrito
     * @throws ArticuloNotFoundException Si el artículo no existe
     * @throws StockInsuficienteException Si no hay stock disponible
     * @throws IllegalArgumentException Si la cantidad es inválida
     */
    @Autowired
    private CarritoService carritoService;

    @Operation(
            summary = "Añadir artículo al carrito",
            description = "Valida stock, crea o recupera el carrito activo y retorna el estado actualizado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Artículo agregado al carrito exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CarritoResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Cantidad inválida o datos de entrada incorrectos",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Artículo no encontrado en el catálogo",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Stock insuficiente para la cantidad solicitada",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        )
    })
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> agregarAlCarrito(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AddToCarritoRequestDTO request) {
        CarritoResponseDTO response = carritoService.agregarACarrito(userDetails.getId(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene el contenido completo del carrito del usuario autenticado.
     *
     * <p>
     * Devuelve todos los artículos en el carrito con sus cantidades, precios
     * unitarios, subtotales y el total general del carrito.</p>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado
     * automáticamente)
     * @return DTO con el contenido completo del carrito
     */
    @Operation(
            summary = "Ver carrito de compras",
            description = "Obtiene el contenido completo del carrito del usuario autenticado con totales calculados"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Carrito obtenido exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CarritoResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "El usuario no tiene un carrito activo",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        )
    })
    @GetMapping
    public CarritoResponseDTO verCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return carritoService.verCarrito(userDetails.getId());
    }

    /**
     * Decrementa la cantidad de un artículo específico en el carrito.
     *
     * <p>
     * Reduce en 1 unidad la cantidad del artículo especificado. Si la cantidad
     * resultante es 0, el artículo se elimina completamente del carrito.</p>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado
     * automáticamente)
     * @param idArticulo ID único del artículo a decrementar
     * @return ResponseEntity con el estado actualizado del carrito
     * @throws ArticuloNotFoundException Si el artículo no está en el carrito
     */
    @Operation(
            summary = "Decrementar cantidad de artículo",
            description = "Reduce en 1 la cantidad del artículo especificado. Si llega a 0, se elimina del carrito"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Cantidad decrementada exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CarritoResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Artículo no encontrado en el carrito",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "ID de artículo inválido"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        )
    })
    @PostMapping("/{idArticulo}")
    public ResponseEntity<CarritoResponseDTO> decrementarArticulo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long idArticulo) {
        CarritoResponseDTO response = carritoService.decrementarArticulo(userDetails.getId(), idArticulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Vacía completamente el carrito del usuario autenticado.
     *
     * <p>
     * Elimina todos los artículos del carrito activo del usuario. Esta
     * operación es irreversible.</p>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado
     * automáticamente)
     * @return ResponseEntity vacío con código 204 (No Content)
     */
    @Operation(
            summary = "Vaciar carrito completo",
            description = "Elimina todos los artículos del carrito del usuario. Esta operación es irreversible"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Carrito vaciado exitosamente"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "El usuario no tiene un carrito activo",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        )
    })
    @DeleteMapping
    public ResponseEntity<Void> vaciarCarrito(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        carritoService.vaciarCarrito(userDetails.getId());
        return ResponseEntity.noContent().build();
    }

}
