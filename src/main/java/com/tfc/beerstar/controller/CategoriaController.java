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
 * Controlador REST para gestionar las categorías del sistema.
 * 
 * <p>Este controlador expone endpoints para crear, leer, actualizar y eliminar
 * categorías, permitiendo a los clientes interactuar con la API de forma estructurada.</p>
 * 
 * <p>Todos los métodos retornan objetos {@link ResponseEntity}, permitiendo el manejo
 * flexible del estado HTTP y la respuesta del cuerpo.</p>
 * 
 * Endpoints disponibles:
 * <ul>
 *   <li> POST	/beerstar/categorias/crearCategoria	-> Crea una nueva categoría. </li>
 *   <li> GET	/beerstar/categorias/obtenerCategoria/{id}	-> Obtiene una categoría por su ID. </li>
 *   <li> GET	/beerstar/categorias/listarCategorias	-> Retorna todas las categorías registradas. </li>
 *   <li> PUT	/beerstar/categorias/actualizarCategoria/{id}	-> Actualiza una categoría existente por su ID. </li>
 *   <li> DELETE	/beerstar/categorias/eliminarCategoria/{id}	-> Elimina una categoría por su ID. </li>
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
    @PostMapping("/crearCategoria")
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
    @GetMapping("/obtenerCategoria/{categoriaId}")
    public ResponseEntity<CategoriasResponseDTO> obtenerCategoriaPorId(@PathVariable Long categoriaId) {
        CategoriasResponseDTO response = categoriaService.obtenerCategoriaPorId(categoriaId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todas las categorías registradas en el sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todas las categorías.
     */
    @GetMapping("/listarCategorias")
    public ResponseEntity<List<CategoriasResponseDTO>> obtenerTodasLasCategorias() {
        List<CategoriasResponseDTO> response = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza una categoría existente por su ID.
     *
     * @param categoriaId ID de la categoría a actualizar.
     * @param categoriasRequestDTO DTO con los nuevos datos de la categoría.
     * @return DTO de respuesta con los datos actualizados de la categoría.
     */
    @PutMapping("/actualizarCategoria/{categoriaId}")
    public ResponseEntity<CategoriasResponseDTO> actualizarCategoria(@PathVariable Long categoriaId, @Valid @RequestBody CategoriasRequestDTO categoriasRequestDTO) {
        CategoriasResponseDTO response = categoriaService.actualizarCategoria(categoriaId, categoriasRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param categoriaId ID de la categoría a eliminar.
     * @return Mensaje de confirmación de eliminación.
     */
    @DeleteMapping("/eliminarCategoria/{categoriaId}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable("categoriaId") Long categoriaId) {
        categoriaService.eliminarCategoria(categoriaId);
        return ResponseEntity.ok("Categoria eliminada correctamente");

    }
}
