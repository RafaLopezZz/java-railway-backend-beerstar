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


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("beerstar/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @PostMapping("/crearLote")
    public ResponseEntity<LoteResponseDTO> crearLote(@Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.crearLote(lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerLote/{loteId}")
    public ResponseEntity<LoteResponseDTO> obtenerLotePorId(@PathVariable Long loteId) {
        LoteResponseDTO response = loteService.obtenerLotePorId(loteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarLotes")
    public ResponseEntity<List<LoteResponseDTO>> obtenerTodosLosLotes() {
        List<LoteResponseDTO> response = loteService.obtenerTodosLosLotes();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizarLote/{loteId}")
    public ResponseEntity<LoteResponseDTO> actualizarLote(@PathVariable Long loteId, @Valid @RequestBody LoteRequestDTO lotesRequestDTO) {
        LoteResponseDTO response = loteService.actualizarLote(loteId, lotesRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarLote/{loteId}")
    public ResponseEntity<String> eliminarLote(@PathVariable("loteId") Long loteId) {
        loteService.eliminarLote(loteId);
        return ResponseEntity.ok("Lote eliminado correctamente");
    }
}
