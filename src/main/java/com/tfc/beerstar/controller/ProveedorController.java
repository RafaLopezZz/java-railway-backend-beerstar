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

import com.tfc.beerstar.dto.request.ProveedorRequestDTO;
import com.tfc.beerstar.dto.response.MessageResponse;
import com.tfc.beerstar.dto.response.ProveedorResponseDTO;
import com.tfc.beerstar.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de datos de proveedores asociados a
 * usuarios.
 *
 * <p>
 * Proporciona endpoints para obtener, listar y actualizar la información del
 * proveedor correspondiente a un usuario determinado. Los proveedores están
 * vinculados a usuarios mediante una relación uno a uno y contienen información
 * específica del negocio como datos fiscales, información de contacto
 * comercial, etc.</p>
 *
 * <p>
 * Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir
 * peticiones desde cualquier origen.</p>
 *
 * <p>
 * Endpoints disponibles:</p>
 * <ul>
 * <li>GET /rlp/usuarios/proveedores → Listar todos los proveedores</li>
 * <li>GET /rlp/usuarios/proveedores/{idUsuario} → Obtener datos de proveedor
 * por ID de usuario</li>
 * <li>PUT /rlp/usuarios/proveedores/{idUsuario} → Actualizar datos de proveedor
 * por ID de usuario</li>
 * </ul>
 *
 * <p>
 * <strong>Nota:</strong> Todos los endpoints requieren autenticación JWT válida
 * y permisos apropiados para acceder a la información de proveedores.</p>
 *
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rlp/usuarios/proveedores")
@Tag(name = "Proveedores", description = "Gestión de datos de proveedores asociados a usuarios")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene los datos del proveedor asociado al usuario indicado por su ID.
     *
     * <p>
     * Busca el proveedor vinculado al usuario especificado. Cada usuario de
     * tipo PROVEEDOR tiene asociado un registro de proveedor con información
     * comercial adicional como datos fiscales, información de contacto,
     * categorías de productos, etc.</p>
     *
     * @param idUsuario ID único del usuario cuyo proveedor se desea obtener
     * @return ResponseEntity con los datos del proveedor encontrado
     * @throws ProveedorNotFoundException Si no se encuentra proveedor para el
     * usuario especificado
     * @throws UsuarioNotFoundException Si el usuario no existe
     * @throws AccessDeniedException Si el usuario no tiene permisos para
     * acceder a este proveedor
     */
    @Operation(
            summary = "Obtener proveedor por ID de usuario",
            description = "Busca y devuelve los datos del proveedor asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Proveedor encontrado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProveedorResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Proveedor no encontrado para el usuario especificado",
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
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorUsuarioId(@PathVariable Long idUsuario) {
        ProveedorResponseDTO proveedor = proveedorService.obtenerProveedorPorUsuarioId(idUsuario);
        return ResponseEntity.ok(proveedor);
    }

    /**
     * Lista todos los proveedores registrados en el sistema.
     *
     * <p>
     * Devuelve una lista completa de todos los proveedores con su información
     * comercial básica. Este endpoint está disponible para usuarios con
     * permisos administrativos y puede ser utilizado para generar directorios
     * de proveedores o realizar búsquedas administrativas.</p>
     *
     * @return ResponseEntity con la lista de todos los proveedores
     */
    @Operation(
            summary = "Listar todos los proveedores",
            description = "Obtiene una lista completa de todos los proveedores registrados en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Lista de proveedores obtenida exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ProveedorResponseDTO.class))
                )
        ),
        @ApiResponse(
                responseCode = "204",
                description = "No hay proveedores registrados en el sistema"
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
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        List<ProveedorResponseDTO> proveedores = proveedorService.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }

    /**
     * Actualiza los datos del proveedor asociado al usuario indicado por su ID.
     *
     * <p>
     * Modifica la información comercial del proveedor vinculado al usuario
     * especificado. Solo se actualizan los campos proporcionados en el DTO de
     * entrada. Los campos no incluidos mantienen sus valores actuales. Esta
     * operación puede incluir actualización de datos fiscales, información de
     * contacto, categorías de productos ofrecidos, etc.</p>
     *
     * @param idUsuario ID del usuario cuyo proveedor se desea actualizar
     * @param proveedorRequestDTO DTO con los nuevos datos del proveedor a
     * actualizar
     * @return ResponseEntity con los datos actualizados del proveedor
     * @throws UsuarioNotFoundException Si el usuario no existe
     * @throws ValidationException Si los datos de entrada son inválidos
     * @throws AccessDeniedException Si el usuario no tiene permisos para
     * modificar este proveedor
     */
    @Operation(
            summary = "Actualizar datos de proveedor",
            description = "Modifica la información comercial del proveedor asociado al usuario especificado"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Proveedor actualizado exitosamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProveedorResponseDTO.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Proveedor no encontrado para el usuario especificado",
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
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(
            @PathVariable Long idUsuario,
            @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorResponseDTO proveedorActualizado = proveedorService.actualizarProveedor(idUsuario, proveedorRequestDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }
}
