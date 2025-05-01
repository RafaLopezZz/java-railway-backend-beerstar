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

import com.tfc.beerstar.dto.request.LoteRequestDTO;
import com.tfc.beerstar.dto.response.LoteResponseDTO;
import com.tfc.beerstar.service.LoteService;

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar operaciones relacionadas con los lotes.
 *
 * <p>Este controlador expone endpoints para crear, leer, actualizar y eliminar
 * lotes en la aplicación. Todas las rutas están bajo el prefijo
 * <code>/beerstar/lotes</code>.</p>
 * 
 * <p>Permite acceso cross-origin desde cualquier origen (CORS: *).</p>
 * 
 * Endpoints disponibles:
 * <ul>
 *  <li> POST	/beerstar/lotes/crearLote	-> Crea un nuevo lote. </li>
 *  <li> GET	/beerstar/lotes/obtenerLote/{id}	-> Obtiene un lote por su ID. </li>
 *  <li> GET	/beerstar/lotes/listarLotes	-> Retorna todos los lotes registrados. </li>
 *  <li> PUT	/beerstar/lotes/actualizarLote/{id}	-> Actualiza un lote existente por su ID. </li>
 *  <li> DELETE	/beerstar/lotes/eliminarLote/{id}	-> Elimina un lote por su ID. </li>
 * </ul>
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
    @PostMapping("/crearLote")
    public ResponseEntity<LoteResponseDTO> crearLote(@Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.crearLote(lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un lote por su ID.
     *
     * @param loteId ID del lote a recuperar.
     * @return DTO de respuesta con los datos del lote encontrado.
     */
    @GetMapping("/obtenerLote/{loteId}")
    public ResponseEntity<LoteResponseDTO> obtenerLotePorId(@PathVariable Long loteId) {
        LoteResponseDTO response = loteService.obtenerLotePorId(loteId);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los lotes registrados en el sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todos los lotes.
     */
    @GetMapping("/listarLotes")
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
    @PutMapping("/actualizarLote/{loteId}")
    public ResponseEntity<LoteResponseDTO> actualizarLote(@PathVariable Long loteId, @Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.actualizarLote(loteId, lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un lote por su ID.
     *
     * @param loteId ID del lote a eliminar.
     * @return Mensaje de confirmación de eliminación.
     */
    @DeleteMapping("/eliminarLote/{loteId}")
    public ResponseEntity<String> eliminarLote(@PathVariable("loteId") Long loteId) {
        loteService.eliminarLote(loteId);
        return ResponseEntity.ok("Lote eliminado correctamente");
    }
}
