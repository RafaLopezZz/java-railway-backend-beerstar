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
 * Endpoints:
 * <ul>
 *   <li>POST   /beerstar/articulos/crearArticulo        → Crear un nuevo artículo</li>
 *   <li>GET    /beerstar/articulos/obtenerArticulo/{id} → Obtener artículo por ID</li>
 *   <li>GET    /beerstar/articulos/listarArticulos      → Listar todos los artículos</li>
 *   <li>PUT    /beerstar/articulos/actualizarArticulo/{id} → Actualizar artículo por ID</li>
 *   <li>DELETE /beerstar/articulos/eliminarArticulo/{id}  → Eliminar artículo por ID</li>
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
     * Endpoint para crear un nuevo artículo.
     *
     * @param articuloRequestDTO DTO con los datos del artículo a crear. Debe ser válido.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo creado y HTTP 200.
     */
    @PostMapping("/crearArticulo")
    public ResponseEntity<ArticulosResponseDTO> crearArticulo(@Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.crearArticulos(articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener un artículo por su ID.
     *
     * @param articuloId ID del artículo a obtener.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo encontrado y HTTP 200.
     */
    @GetMapping("/obtenerArticulo/{articuloId}")
    public ResponseEntity<ArticulosResponseDTO> obtenerArticuloPorId(@PathVariable Long articuloId) {
        ArticulosResponseDTO response = articuloService.obtenerArticuloPorId(articuloId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para listar todos los artículos.
     *
     * @return {@code ResponseEntity<List<ArticulosResponseDTO>>} con la lista de artículos y HTTP 200.
     */
    @GetMapping("/listarArticulos")
    public ResponseEntity<List<ArticulosResponseDTO>> obtenerTodosLosArticulos() {
        List<ArticulosResponseDTO> response = articuloService.obtenerTodosLosArticulos();
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para actualizar un artículo por su ID.
     *
     * @param articuloId ID del artículo a actualizar.
     * @param articuloRequestDTO DTO con los nuevos datos del artículo. Debe ser válido.
     * @return {@code ResponseEntity<ArticulosResponseDTO>} con el artículo actualizado y HTTP 200.
     */
    @PutMapping("/actualizarArticulo/{articuloId}")
    public ResponseEntity<ArticulosResponseDTO> actualizarArticulo(@PathVariable Long articuloId, @Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.actualizarArticulo(articuloId, articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para eliminar un artículo por su ID.
     *
     * @param articuloId ID del artículo a eliminar.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP 200.
     */
    @DeleteMapping("/eliminarArticulo/{articuloId}")
    public ResponseEntity<String> eliminarArticulo(@PathVariable("articuloId") Long articuloId) {
        articuloService.eliminarArticulo(articuloId);
        return ResponseEntity.ok("Artículo eliminado correctamente");
    }
}
