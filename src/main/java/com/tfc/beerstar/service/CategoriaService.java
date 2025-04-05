package com.tfc.beerstar.service;

import com.tfc.beerstar.repository.CategoriasRepository;
import com.tfc.beerstar.dto.request.CategoriasRequestDTO;
import com.tfc.beerstar.dto.response.CategoriasResponseDTO;
import com.tfc.beerstar.model.Categorias;
import com.tfc.beerstar.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    public CategoriasResponseDTO crearCategoria(CategoriasRequestDTO dto) {
        Categorias categoria = new Categorias();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        Categorias guardado = categoriasRepository.save(categoria);
        return mapearResponseDTO(guardado);
    }

    public CategoriasResponseDTO obtenerCategoriaPorId(Long id) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return mapearResponseDTO(categoria);
    }

    public List<CategoriasResponseDTO> obtenerTodasLasCategorias() {
        List<Categorias> categorias = categoriasRepository.findAll();
        return categorias.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriasResponseDTO actualizarCategoria(Long id, CategoriasRequestDTO dto) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        
        Categorias actualizada = categoriasRepository.save(categoria);
        return mapearResponseDTO(actualizada);
    }

    public void eliminarCategoria(Long id) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        categoriasRepository.delete(categoria);
    }


    private CategoriasResponseDTO mapearResponseDTO(Categorias categoria) {
        CategoriasResponseDTO dto = new CategoriasResponseDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
