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

import com.tfc.beerstar.dto.request.LoteRequestDTO;
import com.tfc.beerstar.dto.response.LoteResponseDTO;
import com.tfc.beerstar.service.LoteService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar operaciones relacionadas con los lotes.
 *
 * <p>Este controlador implementa una API RESTful para la gestión de lotes,
 * siguiendo las convenciones de nomenclatura y estructura de recursos REST.
 * Todas las rutas están bajo el prefijo <code>/api/v1/lotes</code>.</p>
 * 
 * <p>Permite acceso cross-origin desde cualquier origen (CORS: *).</p>
 * 
 * <p>Los endpoints siguen los principios RESTful donde los verbos HTTP
 * representan las acciones sobre el recurso 'lotes':</p>
 * 
 * Endpoints disponibles:
 * <ul>
 *  <li> POST   beerstar/lotes             -> Crea un nuevo lote </li>
 *  <li> GET    beerstar/lotes/{idLote}    -> Obtiene un lote por su ID </li>
 *  <li> GET    beerstar/lotes             -> Retorna todos los lotes registrados </li>
 *  <li> PUT    beerstar/lotes/{idLote}    -> Actualiza un lote existente por su ID </li>
 *  <li> DELETE beerstar/lotes/{idLote}    -> Elimina un lote por su ID </li>
 * </ul>
 * 
 * <p>Cada endpoint incluye validación de datos de entrada y respuestas HTTP
 * semánticamente correctas según el resultado de la operación.</p>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("beerstar/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    /**
     * Crea un nuevo lote en el sistema.
     *
     * @param lotesRequestDTO DTO con los datos del lote a crear.
     * @return DTO de respuesta con los datos del lote creado.
     */
    @PostMapping
    public ResponseEntity<LoteResponseDTO> crearLote(@Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.crearLote(lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un lote por su ID.
     *
     * @param idLote ID del lote a recuperar.
     * @return DTO de respuesta con los datos del lote encontrado.
     */
    @GetMapping("/{idLote}")
    public ResponseEntity<LoteResponseDTO> obtenerLotePorId(@PathVariable Long idLote) {
        LoteResponseDTO response = loteService.obtenerLotePorId(idLote);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los lotes registrados en el sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todos los lotes.
     */
    @GetMapping
    public ResponseEntity<List<LoteResponseDTO>> obtenerTodosLosLotes() {
        List<LoteResponseDTO> response = loteService.obtenerTodosLosLotes();
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un lote existente por su ID.
     *
     * @param loteId ID del lote a actualizar.
     * @param lotesRequestDTO DTO con los nuevos datos del lote.
     * @return DTO de respuesta con los datos del lote actualizado.
     */
    @PutMapping("/{idLote}")
    @PreAuthorize("hasRole('PROVEEDOR')")
    public ResponseEntity<LoteResponseDTO> actualizarLote(@PathVariable Long idLote, @Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.actualizarLote(idLote, lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un lote por su ID.
     *
     * @param idLote ID del lote a eliminar.
     * @return Mensaje de confirmación de eliminación.
     */
    @DeleteMapping("/{idLote}")
    public ResponseEntity<String> eliminarLote(@PathVariable("idLote") Long idLote) {
        loteService.eliminarLote(idLote);
        return ResponseEntity.ok("Lote eliminado correctamente");
    }
}
