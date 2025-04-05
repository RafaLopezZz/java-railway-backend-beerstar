package com.tfc.beerstar.controller;

import com.tfc.beerstar.dto.request.CategoriasRequestDTO;
import com.tfc.beerstar.dto.response.CategoriasResponseDTO;
import com.tfc.beerstar.service.CategoriaService;

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

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/beerstar/categorias")
public class CategoriaController {
// TODO: Implementar los métodos del controlador para manejar las solicitudes HTTP relacionadas con las categorías

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/crearCategoria")
    public ResponseEntity<CategoriasResponseDTO> crearCategoria(@Valid @RequestBody CategoriasRequestDTO categoriasRequestDTO) {
        CategoriasResponseDTO response = categoriaService.crearCategoria(categoriasRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerCategoria/{categoriaId}")
    public ResponseEntity<CategoriasResponseDTO> obtenerCategoriaPorId(@RequestBody Long id) {
        CategoriasResponseDTO response = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarCategorias")
    public ResponseEntity<List<CategoriasResponseDTO>> obtenerTodasLasCategorias() {
        List<CategoriasResponseDTO> response = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizarCategoria/{categoriaId}")
    public ResponseEntity<CategoriasResponseDTO> actualizarCategoria(@RequestBody Long id, @Valid @RequestBody CategoriasRequestDTO categoriasRequestDTO) {
        CategoriasResponseDTO response = categoriaService.actualizarCategoria(id, categoriasRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarCategoria/{categoriaId}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable("categoriaId") Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");

    }
}
