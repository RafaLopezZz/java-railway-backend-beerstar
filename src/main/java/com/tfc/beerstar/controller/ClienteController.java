package com.tfc.beerstar.controller;

import java.util.List;

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
import com.tfc.beerstar.dto.response.MessageResponse;
import com.tfc.beerstar.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de datos de clientes asociados a usuarios.
 *
 * <p>
 * Proporciona endpoints para obtener, listar y actualizar la información del
 * cliente correspondiente a un usuario determinado. Los clientes están
 * vinculados a usuarios mediante una relación uno a uno.</p>
 *
 * <p>
 * Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir
 * peticiones desde cualquier origen.</p>
 *
 * <p>
 * Endpoints disponibles:</p>
 * <ul>
 * <li>GET /rlp/usuarios/clientes → Listar todos los clientes</li>
 * <li>GET /rlp/usuarios/clientes/{idUsuario} → Obtener datos de cliente por ID
 * de usuario</li>
 * <li>PUT /rlp/usuarios/clientes/{idUsuario} → Actualizar datos de cliente por
 * ID de usuario</li>
 * </ul>
 *
 * <p>
 * <strong>Nota:</strong> Todos los endpoints requieren autenticación JWT
 * válida.</p>
 *
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rlp/usuarios/clientes")
@Tag(name = "Clientes", description = "Gestión de datos de clientes asociados a usuarios")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Obtiene los datos del cliente asociado al usuario indicado por su ID.
     *
     * <p>
     * Busca el cliente vinculado al usuario especificado. Cada usuario de tipo
     * CLIENTE tiene asociado un registro de cliente con información adicional
     * como dirección, teléfono, etc.</p>
     *
     * @param idUsuario ID único del usuario cuyo cliente se desea obtener
     * @return ResponseEntity con los datos del cliente encontrado
     * @throws ClienteNotFoundException Si no se encuentra cliente para el
     * usuario especificado
     * @throws UsuarioNotFoundException Si el usuario no existe
     */
    @Operation(
            summary = "Obtener cliente por ID de usuario",
            description = "Busca y devuelve los datos del cliente asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Cliente encontrado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ClienteResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Cliente no encontrado para el usuario especificado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "ID de usuario inválido"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Sin permisos para acceder a este recurso"
        )
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorUsuarioId(@PathVariable Long idUsuario) {
        ClienteResponseDTO cliente = clienteService.obtenerClientePorUsuarioId(idUsuario);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Lista todos los clientes registrados en el sistema.
     *
     * <p>
     * Devuelve una lista completa de todos los clientes con su información
     * básica. Este endpoint está disponible para usuarios con permisos
     * administrativos.</p>
     *
     * @return ResponseEntity con la lista de todos los clientes
     */
    @Operation(
            summary = "Listar todos los clientes",
            description = "Obtiene una lista completa de todos los clientes registrados en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Lista de clientes obtenida exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class))
                )
        ),
        @ApiResponse(
                responseCode = "204",
                description = "No hay clientes registrados en el sistema"
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Usuario no autenticado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Sin permisos para acceder a este recurso"
        )
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Actualiza los datos del cliente asociado al usuario indicado por su ID.
     *
     * <p>
     * Modifica la información del cliente vinculado al usuario especificado.
     * Solo se actualizan los campos proporcionados en el DTO de entrada. Los
     * campos no incluidos mantienen sus valores actuales.</p>
     *
     * @param idUsuario ID del usuario cuyo cliente se desea actualizar
     * @param clienteRequestDTO DTO con los nuevos datos del cliente a
     * actualizar
     * @return ResponseEntity con los datos actualizados del cliente
     * @throws ClienteNotFoundException Si no se encuentra cliente para el
     * usuario especificado
     * @throws UsuarioNotFoundException Si el usuario no existe
     * @throws ValidationException Si los datos de entrada son inválidos
     */
    @Operation(
            summary = "Actualizar datos de cliente",
            description = "Modifica la información del cliente asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Cliente actualizado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ClienteResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Cliente no encontrado para el usuario especificado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos",
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
                responseCode = "403",
                description = "Sin permisos para modificar este recurso"
        )
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long idUsuario,
            @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(idUsuario, clienteRequestDTO);
        return ResponseEntity.ok(clienteActualizado);
    }
}
