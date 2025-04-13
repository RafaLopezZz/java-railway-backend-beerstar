package com.tfc.beerstar.controller;

import com.tfc.beerstar.dto.request.ArticulosRequestDTO;
import com.tfc.beerstar.dto.response.ArticulosResponseDTO;
import com.tfc.beerstar.service.ArticuloService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("beerstar/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @PostMapping("/crearArticulo")
    public ResponseEntity<ArticulosResponseDTO> crearArticulo(@Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.crearArticulos(articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerArticulo/{articuloId}")
    public ResponseEntity<ArticulosResponseDTO> obtenerArticuloPorId(@PathVariable Long articuloId) {
        ArticulosResponseDTO response = articuloService.obtenerArticuloPorId(articuloId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarArticulos")
    public ResponseEntity<List<ArticulosResponseDTO>> obtenerTodosLosArticulos() {
        List<ArticulosResponseDTO> response = articuloService.obtenerTodosLosArticulos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizarArticulo/{articuloId}")
    public ResponseEntity<ArticulosResponseDTO> actualizarArticulo(@PathVariable Long articuloId, @Valid @RequestBody ArticulosRequestDTO articuloRequestDTO) {
        ArticulosResponseDTO response = articuloService.actualizarArticulo(articuloId, articuloRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarArticulo/{articuloId}")
    public ResponseEntity<String> eliminarArticulo(@PathVariable("articuloId") Long articuloId) {
        articuloService.eliminarArticulo(articuloId);
        return ResponseEntity.ok("Artículo eliminado correctamente");
    }

}
