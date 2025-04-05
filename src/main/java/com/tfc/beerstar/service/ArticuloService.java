package com.tfc.beerstar.service;

import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;
import com.tfc.beerstar.dto.request.ArticulosRequestDTO;
import com.tfc.beerstar.dto.response.ArticulosResponseDTO;
import com.tfc.beerstar.repository.ArticulosRepository;
import com.tfc.beerstar.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloService {

    @Autowired
    private ArticulosRepository articuloRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    public ArticulosResponseDTO crearArticulos(ArticulosRequestDTO dto) {
        Articulos articulo = new Articulos();
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setImagen(dto.getImagen());

        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        articulo.setCategoria(categoria);

        Articulos guardado = articuloRepository.save(articulo);
        return mapearResponseDTO(guardado);
    }

    public ArticulosResponseDTO obtenerArticuloPorId(Long id) {
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
        return mapearResponseDTO(articulo);
    }

    public List<ArticulosResponseDTO> obtenerTodosLosArticulos() {
        List<Articulos> articulos = articuloRepository.findAll();
        return articulos.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public ArticulosResponseDTO actualizarArticulo(Long id, ArticulosRequestDTO dto) {
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));

        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
        articulo.setGraduacion(dto.getGraduacion());
        articulo.setImagen(dto.getImagen());

        Categorias categoria = categoriasRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        articulo.setCategoria(categoria);

        Articulos actualizado = articuloRepository.save(articulo);
        return mapearResponseDTO(actualizado);
    }

    public void eliminarArticulo(Long id) {
        Articulos articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado"));
        articuloRepository.delete(articulo);
    }

    private ArticulosResponseDTO mapearResponseDTO(Articulos articulo) {
        ArticulosResponseDTO dto = new ArticulosResponseDTO();
        dto.setIdArticulo(articulo.getIdArticulo());
        dto.setNombre(articulo.getNombre());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setPrecio(articulo.getPrecio());
        dto.setStock(articulo.getStock());
        dto.setGraduacion(articulo.getGraduacion());
        dto.setImagen(articulo.getImagen());
        if (articulo.getCategoria() != null) {
            dto.setIdCategoria(articulo.getCategoria().getIdCategoria());
            dto.setNombreCategoria(articulo.getCategoria().getNombre());
            dto.setDescripcion(articulo.getCategoria().getDescripcion());
        }
        return dto;
    }

}
