package com.rlp.ecommerce.controller;

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

import com.rlp.ecommerce.dto.response.MessageResponse;
import com.rlp.ecommerce.dto.response.PedidoResponseDTO;
import com.rlp.ecommerce.exception.StockInsuficienteException;
import com.rlp.ecommerce.security.UserDetailsImpl;
import com.rlp.ecommerce.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de pedidos en RLP eCommerce.
 *
 * <p>Proporciona endpoints para crear y consultar pedidos de usuarios autenticados.
 * Los pedidos se generan a partir del carrito activo del usuario y procesan
 * automáticamente el inventario y cálculos de totales.</p>
 *
 * <p>Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir peticiones
 * desde cualquier origen.</p>
 *
 * <p>Endpoints disponibles:</p>
 * <ul>
 *   <li>POST /rlp/pedidos → Crear pedido desde carrito activo</li>
 *   <li>GET  /rlp/pedidos → Listar pedidos del usuario autenticado</li>
 * </ul>
 *
 * <p><strong>Nota:</strong> Todos los endpoints requieren autenticación JWT válida.</p>
 * 
 * @author rafalopezzz
 * @since 1.0.0
 */
@RestController
@RequestMapping("/rlp/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Gestión de pedidos y procesamiento de compras")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

        /**
     * Crea un nuevo pedido a partir del carrito activo del usuario autenticado.
     *
     * <p>Este endpoint procesa el carrito del usuario y genera un pedido formal.
     * Durante el proceso se realizan las siguientes operaciones:</p>
     * <ul>
     *   <li>Validación de stock disponible para todos los artículos</li>
     *   <li>Cálculo de totales y aplicación de descuentos</li>
     *   <li>Actualización del inventario</li>
     *   <li>Vaciado del carrito una vez confirmado el pedido</li>
     *   <li>Generación de número de pedido único</li>
     * </ul>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado automáticamente)
     * @param metodoPago Método de pago seleccionado (TARJETA, EFECTIVO, TRANSFERENCIA).
     *                   Por defecto es "TARJETA" si no se especifica
     * @throws IllegalArgumentException si el carrito está vacío o el método de pago es inválido
     * @throws StockInsuficienteException si no hay suficiente stock para uno o más artículos
     * @throws ResourceNotFoundException si el usuario no existe o no tiene un carrito activo
     * @return ResponseEntity con los datos del pedido creado incluyendo número de pedido,
     *         artículos, totales y estado
     */
    @Operation(
        summary = "Crear pedido desde carrito",
        description = "Procesa el carrito activo del usuario y genera un pedido formal con validación de stock y cálculo de totales"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "Pedido creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Carrito vacío o método de pago inválido",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Stock insuficiente para uno o más artículos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Usuario no autenticado"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        )
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false, defaultValue = "TARJETA") String metodoPago) {
        PedidoResponseDTO pedidoCreado = pedidoService.crearPedido(userDetails.getId(), metodoPago);
        return ResponseEntity.ok(pedidoCreado);
    }

        /**
     * Lista todos los pedidos del usuario autenticado.
     *
     * <p>Devuelve un historial completo de todos los pedidos realizados por el usuario,
     * ordenados por fecha de creación (más recientes primero). Incluye información
     * detallada de cada pedido como artículos, cantidades, precios, estado actual
     * y método de pago utilizado.</p>
     *
     * @param userDetails Detalles del usuario autenticado (inyectado automáticamente)
     * @return ResponseEntity con la lista de pedidos del usuario, incluyendo
     *         detalles completos de cada pedido
     * @throws IllegalArgumentException si el usuario no tiene pedidos registrados
     * @throws ResourceNotFoundException si el usuario no existe en el sistema
     */
    @Operation(
        summary = "Listar pedidos del usuario",
        description = "Obtiene el historial completo de pedidos del usuario autenticado ordenados por fecha"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de pedidos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class))
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "El usuario no tiene pedidos registrados"
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Usuario no autenticado"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        )
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(userDetails.getId());
        return ResponseEntity.ok(pedidos);
    }
}