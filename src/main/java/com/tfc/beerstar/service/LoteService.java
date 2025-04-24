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

@Slf4j
@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public LoteResponseDTO crearLote(LoteRequestDTO lDto) {
        log.info("Creando lote: {}", lDto.getNombreLote());
        Lotes lote = new Lotes();
        lote.setDescripcion(lDto.getDescripcion());
        lote.setPrecio(lDto.getPrecio());
        lote.setUrl(lDto.getUrl());

        Proveedor proveedor = proveedorRepository.findById(lDto.getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        lote.setProveedor(proveedor);
        log.error("Proveedor no encontrado para el id: {}", lDto.getIdProveedor());

        Lotes guardado = loteRepository.save(lote);
        log.info("Lote creado con éxito, id: {}", guardado.getIdLote());
        return mapearResponseDTO(guardado);
    }

    public LoteResponseDTO obtenerLotePorId(Long id) {
        log.info("Obteniendo lote por id: {}", id);
        Lotes lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado"));
        log.error("Lote no encontrado para el id: {}", id);
        return mapearResponseDTO(lote);
    }

    public List<LoteResponseDTO> obtenerTodosLosLotes() {
        log.info("Obteniendo todos los lotes");
        List<Lotes> lotes = loteRepository.findAll();
        log.debug("Cantidad de lotes encontrados: {}", lotes.size());
        return lotes.stream().map(this::mapearResponseDTO).toList();
    }

    public List<LoteResponseDTO> obtenerLotesPorProveedor(Long idProveedor) {
        log.info("Obteniendo lotes por proveedor id: {}", idProveedor);
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        log.error("Proveedor no encontrado para el id: {}", idProveedor);
        List<Lotes> lotes = loteRepository.findByProveedor(proveedor);
        return lotes.stream().map(this::mapearResponseDTO).toList();
    }

    public LoteResponseDTO actualizarLote(Long id, LoteRequestDTO lDto) {
        log.info("Actualizando lote id: {}", id);
        Lotes lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado"));
        log.error("Lote no encontrado para el id: {}", id);

        lote.setDescripcion(lDto.getDescripcion());
        lote.setPrecio(lDto.getPrecio());
        lote.setUrl(lDto.getUrl());

        Proveedor proveedor = proveedorRepository.findById(lDto.getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        lote.setProveedor(proveedor);
        log.error("Proveedor no encontrado para el id: {}", lDto.getIdProveedor());

        Lotes guardado = loteRepository.save(lote);
        log.info("Lote actualizado con éxito, id: {}", guardado.getIdLote());
        return mapearResponseDTO(guardado);
    }

    public void eliminarLote(Long id) {
        log.info("Eliminando lote id: {}", id);
        Lotes lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado"));
        log.error("Lote no encontrado para el id: {}", id);
        loteRepository.delete(lote);
        log.info("Lote eliminado con éxito, id: {}", id);
    }

    private LoteResponseDTO mapearResponseDTO(Lotes lote) {
        LoteResponseDTO dto = new LoteResponseDTO();
        dto.setIdLote(lote.getIdLote());
        dto.setNombreLote(lote.getNombreLote());
        dto.setDescripcion(lote.getDescripcion());
        dto.setPrecio(lote.getPrecio());
        dto.setUrl(lote.getUrl());
        //dto.setFechaValidez(lote.getFechaValidez());
        if(lote.getProveedor() != null) {
            dto.setIdProveedor(lote.getProveedor().getId_proveedor());
            dto.setNombreProveedor(lote.getProveedor().getNombre());
        } else {
            dto.setIdProveedor(null);
            dto.setNombreProveedor(null);
        }

        return dto;
    }
}
