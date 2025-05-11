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

import com.tfc.beerstar.dto.request.CategoriasRequestDTO;
import com.tfc.beerstar.dto.response.CategoriasResponseDTO;
import com.tfc.beerstar.service.CategoriaService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar categorías en Beerstar.
 *
 * <p>Endpoints RESTful:</p>
 * <ul>
 *   <li>POST   /beerstar/categorias        → Crear categoría</li>
 *   <li>GET    /beerstar/categorias/{id}   → Obtener categoría por ID</li>
 *   <li>GET    /beerstar/categorias        → Listar categorías</li>
 *   <li>PUT    /beerstar/categorias/{id}   → Actualizar categoría</li>
 *   <li>DELETE /beerstar/categorias/{id}   → Eliminar categoría</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param categoriasRequestDTO DTO con los datos de la categoría a crear.
     * @return DTO de respuesta con los datos de la categoría creada.
     */
    @PostMapping
    public ResponseEntity<CategoriasResponseDTO> crearCategoria(@Valid @RequestBody CategoriasRequestDTO categoriasRequestDTO) {
        CategoriasResponseDTO response = categoriaService.crearCategoria(categoriasRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param categoriaId ID de la categoría a buscar.
     * @return DTO de respuesta con los datos de la categoría encontrada.
     */
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriasResponseDTO> obtenerCategoriaPorId(@PathVariable Long idCategoria) {
        CategoriasResponseDTO response = categoriaService.obtenerCategoriaPorId(idCategoria);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todas las categorías registradas en el sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todas las categorías.
     */
    @GetMapping
    public ResponseEntity<List<CategoriasResponseDTO>> obtenerTodasLasCategorias() {
        List<CategoriasResponseDTO> response = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza una categoría existente por su ID.
     *
     * @param idCategoria ID de la categoría a actualizar.
     * @param categoriasRequestDTO DTO con los nuevos datos de la categoría.
     * @return DTO de respuesta con los datos actualizados de la categoría.
     */
    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriasResponseDTO> actualizarCategoria(@PathVariable Long idCategoria, @Valid @RequestBody CategoriasRequestDTO categoriasRequestDTO) {
        CategoriasResponseDTO response = categoriaService.actualizarCategoria(idCategoria, categoriasRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param idCategoria ID de la categoría a eliminar.
     * @return Mensaje de confirmación de eliminación.
     */
    @DeleteMapping("{idCategoria}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable("idCategoria") Long idCategoria) {
        categoriaService.eliminarCategoria(idCategoria);
        return ResponseEntity.ok("Categoria eliminada correctamente");

    }
}
