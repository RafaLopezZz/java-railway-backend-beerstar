package com.tfc.beerstar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.ArticulosRequestDTO;
import com.tfc.beerstar.dto.response.ArticulosResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.CategoriasRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticuloService {

    @Autowired
    private ArticulosRepository articuloRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    public ArticulosResponseDTO crearArticulos(ArticulosRequestDTO dto) {
        log.info("Creando artículo: {}", dto.getNombre());
        Articulos articulo = new Articulos();
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setUrl(dto.getUrl());

        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
                log.error("Categoría no encontrada para el id: {}", dto.getIdCategoria());
        articulo.setCategoria(categoria);
        

        Articulos guardado = articuloRepository.save(articulo);
        log.info("Artículo creado con éxito, id: {}", guardado.getIdArticulo());
        return mapearResponseDTO(guardado);
    }

    public ArticulosResponseDTO obtenerArticuloPorId(Long id) {
        log.info("Obteniendo artículo por id: {}", id);
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
                log.error("Artículo no encontrado para el id: {}", id);
        return mapearResponseDTO(articulo);
    }

    public List<ArticulosResponseDTO> obtenerTodosLosArticulos() {
        log.info("Obteniendo todos los artículos");
        List<Articulos> articulos = articuloRepository.findAll();
        log.debug("Cantidad de artículos encontrados: {}", articulos.size());
        return articulos.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public ArticulosResponseDTO actualizarArticulo(Long id, ArticulosRequestDTO dto) {
        log.info("Actualizando artículo id: {}", id);
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
                log.error("Artículo no encontrado para actualizar, id: {}", id);

        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setUrl(dto.getUrl());

        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        articulo.setCategoria(categoria);

        Articulos actualizado = articuloRepository.save(articulo);
        return mapearResponseDTO(actualizado);
    }

    public void eliminarArticulo(Long id) {
        log.info("Eliminando artículo id: {}", id);
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
                log.error("Artículo no encontrado para eliminar, id: {}", id);
        articuloRepository.delete(articulo);
        log.info("Artículo eliminado, id: {}", id);
    }

    private ArticulosResponseDTO mapearResponseDTO(Articulos articulo) {
        ArticulosResponseDTO dto = new ArticulosResponseDTO();
        dto.setIdArticulo(articulo.getIdArticulo());
        dto.setNombre(articulo.getNombre());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setPrecio(articulo.getPrecio());
        dto.setStock(articulo.getStock());
        dto.setGraduacion(articulo.getGraduacion());
        dto.setUrl(articulo.getUrl());
        if (articulo.getCategoria() != null) {
            dto.setIdCategoria(articulo.getCategoria().getIdCategoria());
            dto.setNombreCategoria(articulo.getCategoria().getNombre());
            dto.setDescripcion(articulo.getCategoria().getDescripcion());
        }
        return dto;
    }

}
