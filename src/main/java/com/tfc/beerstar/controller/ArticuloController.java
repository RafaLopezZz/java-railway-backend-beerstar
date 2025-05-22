package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ArticulosRequestDTO;
import com.tfc.beerstar.dto.response.ArticulosResponseDTO;
import com.tfc.beerstar.service.ArticuloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de artículos en Beerstar.
 *
 * <p>
 * Proporciona endpoints para crear, obtener, listar, actualizar y eliminar
 * artículos. Utiliza {@link ArticuloService} para delegar la lógica de
 * negocio.</p>
 *
 * <p>
 * Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir
 * peticiones desde cualquier origen.</p>
 *
 * Endpoints disponibles:
 * <ul>
 * <li>POST /beerstar/articulos → Crear un nuevo artículo</li>
 * <li>GET /beerstar/articulos/{idArticulo} → Obtener artículo por ID</li>
 * <li>GET /beerstar/articulos → Listar todos los artículos</li>
 * <li>PUT /beerstar/articulos/{idArticulo} → Actualizar artículo por ID</li>
 * <li>DELETE /beerstar/articulos/{idArticulo} → Eliminar artículo por ID</li>
 * </ul>
 *
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("beerstar/articulos")
@Tag(name = "Artículos", description = "API para gestionar los artículos de Beerstar")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    /**
     * Crea un nuevo artículo.
     *
     * @param articuloRequestDTO DTO con los datos del artículo. Debe ser
     * válido.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo
     * creado y HTTP 200.
     */
    @Operation(summary = "Crear un nuevo artículo",
            description = "Crea un nuevo artículo con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
                description = "Artículo creado correctamente",
                content = @Content(schema = @Schema(implementation = ArticulosResponseDTO.class))),
        @ApiResponse(responseCode = "400",
                description = "Datos de artículo inválidos",
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<ArticulosResponseDTO> crearArticulo(@Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.crearArticulos(articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un artículo por su ID.
     *
     * @param idArticulo ID del artículo a obtener.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo
     * encontrado y HTTP 200.
     */
    @Operation(summary = "Obtener artículo por ID",
            description = "Retorna un artículo según el ID proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Artículo encontrado",
                content = @Content(schema = @Schema(implementation = ArticulosResponseDTO.class))),
        @ApiResponse(responseCode = "404",
                description = "Artículo no encontrado",
                content = @Content)
    })
    @GetMapping("/{idArticulo}")
    public ResponseEntity<ArticulosResponseDTO> obtenerArticuloPorId(@PathVariable Long idArticulo) {
        ArticulosResponseDTO response = articuloService.obtenerArticuloPorId(idArticulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los artículos.
     *
     * @return {@code ResponseEntity<List<ArticulosResponseDTO>>} con la lista
     * de artículos y HTTP 200.
     */
    @Operation(summary = "Obtener todos los artículos",
            description = "Retorna una lista de todos los artículos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Lista de artículos obtenida correctamente",
                content = @Content(schema = @Schema(implementation = ArticulosResponseDTO.class))),
        @ApiResponse(responseCode = "404",
                description = "No se encontraron artículos",
                content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ArticulosResponseDTO>> obtenerTodosLosArticulos() {
        List<ArticulosResponseDTO> response = articuloService.obtenerTodosLosArticulos();
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un artículo por su ID.
     *
     * @param idArticulo ID del artículo a eliminar.
     * @param articuloRequestDTO DTO con los datos del artículo. Debe ser
     * válido.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP
     * 200.
     */
    @Operation(summary = "Actualizar un artículo existente",
            description = "Actualiza los datos de un artículo existente según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Artículo actualizado correctamente",
                content = @Content(schema = @Schema(implementation = ArticulosResponseDTO.class))),
        @ApiResponse(responseCode = "404",
                description = "Artículo no encontrado",
                content = @Content),
        @ApiResponse(responseCode = "400",
                description = "Datos de artículo inválidos",
                content = @Content)
    })
    @PutMapping("/{idArticulo}")
    public ResponseEntity<ArticulosResponseDTO> actualizarArticulo(@PathVariable Long idArticulo, @Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.actualizarArticulo(idArticulo, articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un artículo por su ID.
     *
     * @param articuloId ID del artículo a eliminar.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP
     * 200.
     */
    @Operation(summary = "Eliminar un artículo",
            description = "Elimina un artículo según el ID proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
                description = "Artículo eliminado correctamente",
                content = @Content),
        @ApiResponse(responseCode = "404",
                description = "Artículo no encontrado",
                content = @Content)
    })
    @DeleteMapping("/{idArticulo}")
    public ResponseEntity<String> eliminarArticulo(@PathVariable("idArticulo") Long idArticulo) {
        articuloService.eliminarArticulo(idArticulo);
        return ResponseEntity.ok("Artículo eliminado correctamente");
    }
}
