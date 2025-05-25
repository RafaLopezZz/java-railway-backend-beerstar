package com.rlp.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlp.ecommerce.dto.request.CategoriasRequestDTO;
import com.rlp.ecommerce.dto.response.CategoriasResponseDTO;
import com.rlp.ecommerce.exception.ResourceNotFoundException;
import com.rlp.ecommerce.model.Categorias;
import com.rlp.ecommerce.repository.CategoriasRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para gestionar las operaciones relacionadas con las categorías.
 * <p>
 * Incluye operaciones de creación, obtención, listado, actualización y
 * eliminación de categorías. Utiliza {@link CategoriasRepository} como capa de
 * acceso a datos.
 * </p>
 *
 * @author rafalopezzz
 */
@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param dto DTO con los datos de la categoría a crear.
     * @return DTO de respuesta con los datos de la categoría creada.
     */
    public CategoriasResponseDTO crearCategoria(CategoriasRequestDTO dto) {
        log.info("Creando categoría: {}", dto.getNombre());
        Categorias categoria = new Categorias();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        Categorias guardado = categoriasRepository.save(categoria);
        log.info("Categoría creada, id: {}", guardado.getIdCategoria());
        return mapearResponseDTO(guardado);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id ID de la categoría a buscar.
     * @return DTO de respuesta con los datos de la categoría encontrada.
     * @throws ResourceNotFoundException si no se encuentra la categoría.
     */
    public CategoriasResponseDTO obtenerCategoriaPorId(Long id) {
        log.info("Obteniendo categoría por id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para el id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        return mapearResponseDTO(categoria);
    }

    /**
     * Obtiene todas las categorías del sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todas las categorías.
     */
    public List<CategoriasResponseDTO> obtenerTodasLasCategorias() {
        log.info("Obteniendo todas las categorías");
        List<Categorias> categorias = categoriasRepository.findAll();
        log.debug("Cantidad de categorías encontradas: {}", categorias.size());
        return categorias.stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca categorías por su nombre.
     *
     * @param nombre Nombre de la categoría a buscar.
     * @return Lista de DTOs de respuesta con los datos de las categorías
     * encontradas.
     * @throws ResourceNotFoundException si no se encuentran categorías.
     */
    public CategoriasResponseDTO actualizarCategoria(Long id, CategoriasRequestDTO dto) {
        log.info("Actualizando categoría id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para actualizar, id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categorias actualizada = categoriasRepository.save(categoria);
        log.info("Categoría actualizada, id: {}", actualizada.getIdCategoria());
        return mapearResponseDTO(actualizada);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar.
     * @throws ResourceNotFoundException si no se encuentra la categoría.
     */
    public void eliminarCategoria(Long id) {
        log.info("Eliminando categoría id: {}", id);
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para eliminar, id: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada");
                });
        categoriasRepository.delete(categoria);
        log.info("Categoría eliminada, id: {}", id);
    }

    /**
     * Convierte una entidad {@link Categorias} en un DTO de respuesta.
     *
     * @param categoria La entidad a convertir.
     * @return DTO de respuesta con los datos de la categoría.
     */
    private CategoriasResponseDTO mapearResponseDTO(Categorias categoria) {
        CategoriasResponseDTO dto = new CategoriasResponseDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
