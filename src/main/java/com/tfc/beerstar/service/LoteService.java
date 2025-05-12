package com.tfc.beerstar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.LoteRequestDTO;
import com.tfc.beerstar.dto.response.LoteResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Lotes;
import com.tfc.beerstar.model.Proveedor;
import com.tfc.beerstar.repository.LoteRepository;
import com.tfc.beerstar.repository.ProveedorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que maneja la lógica de negocio relacionada con los lotes.
 *
 * <p>
 * Este servicio permite crear, actualizar, eliminar y recuperar lotes,
 * trabajando en conjunto con el repositorio de lotes y de proveedores.</p>
 *
 * <p>
 * Usa DTOs para encapsular datos entre las capas y asegura que las operaciones
 * estén validadas y registradas con logs.</p>
 *
 * @author rafalopezzz
 */
@Slf4j
@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Crea un nuevo lote en el sistema.
     *
     * @param lDto DTO con los datos del lote a crear.
     * @return DTO de respuesta con los datos del lote creado.
     */
    public LoteResponseDTO crearLote(LoteRequestDTO lDto) {
        log.info("Creando Lote {}", lDto.getNombreLote());

        Lotes lote = new Lotes();
        lote.setNombreLote(lDto.getNombreLote());
        lote.setDescripcion(lDto.getDescripcion());
        lote.setPrecio(lDto.getPrecio());
        lote.setUrl(lDto.getUrl());

        Proveedor proveedor = proveedorRepository.findById(lDto.getIdProveedor())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado para el id: {}", lDto.getIdProveedor());
                    return new ResourceNotFoundException("Proveedor no encontrado");
                });

        lote.setProveedor(proveedor);
        Lotes guardado = loteRepository.save(lote);
        return mapearResponseDTO(loteRepository.save(guardado));
    }

    /**
     * Obtiene un lote por su ID.
     *
     * @param id ID del lote a buscar.
     * @return DTO de respuesta con los datos del lote encontrado.
     * @throws ResourceNotFoundException si no se encuentra el lote.
     */
    public LoteResponseDTO obtenerLotePorId(Long id) {
        log.info("Obteniendo lote por id: {}", id);
        Lotes lote = loteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Lote no encontrado para el id: {}", id);
                    return new ResourceNotFoundException("Lote no encontrado");
                });
        return mapearResponseDTO(lote);
    }

    /**
     * Obtiene todos los lotes registrados en el sistema.
     *
     * @return Lista de DTOs de respuesta con los datos de todos los lotes.
     */
    public List<LoteResponseDTO> obtenerTodosLosLotes() {
        log.info("Obteniendo todos los lotes");
        List<Lotes> lotes = loteRepository.findAll();
        log.debug("Cantidad de lotes encontrados: {}", lotes.size());
        return lotes.stream().map(this::mapearResponseDTO).toList();
    }

    /**
     * Obtiene todos los lotes asociados a un proveedor específico.
     *
     * @param idProveedor ID del proveedor.
     * @return Lista de DTOs de respuesta con los datos de los lotes del
     * proveedor.
     * @throws ResourceNotFoundException si no se encuentra el proveedor.
     */
    public List<LoteResponseDTO> obtenerLotesPorProveedor(Long idProveedor) {
        log.info("Obteniendo lotes por proveedor id: {}", idProveedor);
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado para el id: {}", idProveedor);
                    return new ResourceNotFoundException("Proveedor no encontrado");
                });
        List<Lotes> lotes = loteRepository.findByProveedor(proveedor);
        return lotes.stream().map(this::mapearResponseDTO).toList();
    }

    public LoteResponseDTO actualizarLote(Long idLote, LoteRequestDTO lDto) {
        log.info("Actualizando lote id: {}", idLote);
        Lotes lote = loteRepository.findById(idLote)
                .orElseThrow(() -> {
                    log.error("Lote no encontrado para el id: {}", idLote);
                    return new ResourceNotFoundException("Lote no encontrado");
                });

        lote.setNombreLote(lDto.getNombreLote());        
        lote.setDescripcion(lDto.getDescripcion());
        lote.setPrecio(lDto.getPrecio());
        lote.setUrl(lDto.getUrl());

        Proveedor proveedor = proveedorRepository.findById(lDto.getIdProveedor())
                .orElseThrow(() -> {
                    log.error("Proveedor no encontrado para el id: {}", lDto.getIdProveedor());
                    return new ResourceNotFoundException("Proveedor no encontrado");
                });
        lote.setProveedor(proveedor);

        Lotes guardado = loteRepository.save(lote);
        log.info("Lote actualizado con éxito, id: {}", guardado.getIdLote());
        return mapearResponseDTO(guardado);
    }

    /**
     * Elimina un lote por su ID.
     *
     * @param id ID del lote a eliminar.
     * @throws ResourceNotFoundException si no se encuentra el lote.
     */
    public void eliminarLote(Long id) {
        log.info("Eliminando lote id: {}", id);
        Lotes lote = loteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Lote no encontrado para el id: {}", id);
                    return new ResourceNotFoundException("Lote no encontrado");
                });
        loteRepository.delete(lote);
        log.info("Lote eliminado con éxito, id: {}", id);
    }

    /**
     * Convierte una entidad {@link Lotes} en un {@link LoteResponseDTO}.
     *
     * @param lote Entidad de tipo Lotes.
     * @return DTO de respuesta con los datos del lote.
     */
    private LoteResponseDTO mapearResponseDTO(Lotes lote) {
        LoteResponseDTO lDto = new LoteResponseDTO();
        lDto.setIdLote(lote.getIdLote());
        lDto.setNombreLote(lote.getNombreLote());
        lDto.setDescripcion(lote.getDescripcion());
        lDto.setIdProveedor(lote.getProveedor().getIdProveedor());
        lDto.setNombreProveedor(lote.getProveedor().getNombre());
        lDto.setPrecio(lote.getPrecio());
        lDto.setUrl(lote.getUrl());

        if (lote.getProveedor() != null) {
            lDto.setIdProveedor(lote.getProveedor().getIdProveedor());
            lDto.setNombreProveedor(lote.getProveedor().getNombre());
        } else {
            lDto.setIdProveedor(null);
            lDto.setNombreProveedor(null);
        }

        return lDto;
    }
}
