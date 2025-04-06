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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    public CategoriasResponseDTO crearCategoria(CategoriasRequestDTO dto) {
        log.info("Creando categoría: {}", dto.getNombre());
        Categorias categoria = new Categorias();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        Categorias guardado = categoriasRepository.save(categoria);
        log.info("Categoría creada, id: {}", guardado.getIdCategoria());
        return mapearResponseDTO(guardado);
    }

    public CategoriasResponseDTO obtenerCategoriaPorId(Long id) {
        log.info("Obteniendo categoría por id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
                log.error("Categoría no encontrada para el id: {}", id);
        return mapearResponseDTO(categoria);
    }

    public List<CategoriasResponseDTO> obtenerTodasLasCategorias() {
        log.info("Obteniendo todas las categorías");
        List<Categorias> categorias = categoriasRepository.findAll();
        log.debug("Cantidad de categorías encontradas: {}", categorias.size());
        return categorias.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriasResponseDTO actualizarCategoria(Long id, CategoriasRequestDTO dto) {
        log.info("Actualizando categoría id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
                log.error("Categoría no encontrada para actualizar, id: {}", id);
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        
        Categorias actualizada = categoriasRepository.save(categoria);
        log.info("Categoría actualizada, id: {}", actualizada.getIdCategoria());
        return mapearResponseDTO(actualizada);
    }

    public void eliminarCategoria(Long id) {
        log.info("Eliminando categoría id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
                log.error("Categoría no encontrada para eliminar, id: {}", id);
        categoriasRepository.delete(categoria);
        log.info("Categoría eliminada, id: {}", id);
    }


    private CategoriasResponseDTO mapearResponseDTO(Categorias categoria) {
        CategoriasResponseDTO dto = new CategoriasResponseDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
