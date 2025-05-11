package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de artículos en Beerstar.
 *
 * <p>Proporciona endpoints para crear, obtener, listar, actualizar y eliminar artículos.
 * Utiliza {@link ArticuloService} para delegar la lógica de negocio.</p>
 *
 * <p>Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir
 * peticiones desde cualquier origen.</p>
 * 
 * Endpoints disponibles:
 * <ul>
 *   <li>POST   /beerstar/articulos                   → Crear un nuevo artículo</li>
 *   <li>GET    /beerstar/articulos/{idArticulo}      → Obtener artículo por ID</li>
 *   <li>GET    /beerstar/articulos                   → Listar todos los artículos</li>
 *   <li>PUT    /beerstar/articulos/{idArticulo}      → Actualizar artículo por ID</li>
 *   <li>DELETE /beerstar/articulos/{idArticulo}      → Eliminar artículo por ID</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*", allowedHeaders="*")
@RestController
@RequestMapping("beerstar/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    /**
     * Crea un nuevo artículo.
     *
     * @param articuloRequestDTO DTO con los datos del artículo. Debe ser válido.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo creado y HTTP 200.
     */
    @PostMapping("/{idArticulo}")
    @PreAuthorize("hasRole('PROVEEDOR')")
    public ResponseEntity<ArticulosResponseDTO> crearArticulo(@Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.crearArticulos(articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un artículo por su ID.
     *
     * @param idArticulo ID del artículo a obtener.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo encontrado y HTTP 200.
     */
    @GetMapping("/{idArticulo}")
    public ResponseEntity<ArticulosResponseDTO> obtenerArticuloPorId(@PathVariable Long idArticulo) {
        ArticulosResponseDTO response = articuloService.obtenerArticuloPorId(idArticulo);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los artículos.
     *
     * @return {@code ResponseEntity<List<ArticulosResponseDTO>>} con la lista de artículos y HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<ArticulosResponseDTO>> obtenerTodosLosArticulos() {
        List<ArticulosResponseDTO> response = articuloService.obtenerTodosLosArticulos();
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un artículo por su ID.
     *
     * @param idArticulo ID del artículo a eliminar.
     * @param articuloRequestDTO DTO con los datos del artículo. Debe ser válido.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP 200.
     */
    @PutMapping("/{idArticulo}")
    @PreAuthorize("hasRole('PROVEEDOR')")
    public ResponseEntity<ArticulosResponseDTO> actualizarArticulo(@PathVariable Long idArticulo, @Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.actualizarArticulo(idArticulo, articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un artículo por su ID.
     *
     * @param articuloId ID del artículo a eliminar.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP 200.
     */
    @DeleteMapping("/{idArticulo}")
    @PreAuthorize("hasRole('PROVEEDOR')")
    public ResponseEntity<String> eliminarArticulo(@PathVariable("idArticulo") Long idArticulo) {
        articuloService.eliminarArticulo(idArticulo);
        return ResponseEntity.ok("Artículo eliminado correctamente");
    }
}
