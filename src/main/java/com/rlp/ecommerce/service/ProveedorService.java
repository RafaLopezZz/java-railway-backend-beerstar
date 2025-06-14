package com.rlp.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlp.ecommerce.dto.request.ProveedorRequestDTO;
import com.rlp.ecommerce.dto.response.ProveedorResponseDTO;
import com.rlp.ecommerce.dto.response.UsuarioResponseDTO;
import com.rlp.ecommerce.exception.ResourceNotFoundException;
import com.rlp.ecommerce.model.Proveedor;
import com.rlp.ecommerce.model.Usuario;
import com.rlp.ecommerce.repository.ProveedorRepository;


/**
 * Servicio para la gestión de proveedores.
 * 
 * <p>Proporciona operaciones como crear, obtener, actualizar, eliminar y listar
 * proveedores. Además, mapea entidades {@link Proveedor} a DTOs y viceversa.</p>
 * 
 * Este servicio se utiliza principalmente en conjunto con {@link UsuarioService}
 * cuando se registran nuevos usuarios del tipo PROVEEDOR.
 * 
 * @author rafalopezzz
 */
@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

        /**
     * Crea y guarda un nuevo proveedor en base a los datos proporcionados.
     * 
     * @param usuario El usuario asociado al proveedor.
     * @param pDto DTO con los datos del proveedor.
     * @return DTO de respuesta con los datos del proveedor creado.
     */
    public ProveedorResponseDTO crearProveedor(Usuario usuario, ProveedorRequestDTO pDto) {
        if (pDto == null) {
            pDto = new ProveedorRequestDTO(); // Inicializar si es nulo para evitar errores
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setNombre(pDto.getNombre());
        proveedor.setDireccion(pDto.getDireccion());
        proveedor.setTelefono(pDto.getTelefono());
        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setUrl(pDto.getUrl());

        Proveedor saved = proveedorRepository.save(proveedor);
        return mapearResponseDTO(saved);
    }

    /**
     * Busca un proveedor por el ID del usuario asociado.
     * 
     * @param usuarioId ID del usuario relacionado.
     * @return DTO con los datos del proveedor.
     * @throws ResourceNotFoundException si no se encuentra el proveedor.
     */
    public ProveedorResponseDTO obtenerProveedorPorUsuarioId(Long idProveedor) {
        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado para el usuario con ID: " + idProveedor));
        
        return mapearResponseDTO(proveedor);
    }

    /**
     * Lista todos los proveedores registrados en el sistema.
     * 
     * @return Lista de DTOs con los datos de todos los proveedores.
     */
    public List<ProveedorResponseDTO> listarProveedores() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los datos de un proveedor existente.
     * 
     * @param id ID del proveedor a actualizar.
     * @param dto DTO con los nuevos datos del proveedor.
     * @return DTO actualizado del proveedor.
     * @throws RuntimeException si el proveedor no existe.
     */
    public ProveedorResponseDTO actualizarProveedor(Long idUsuario, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setUrl(dto.getUrl());
        
        Proveedor actualizado = proveedorRepository.save(proveedor);
        return mapearResponseDTO(actualizado);
    }

    /**
     * Elimina un proveedor por su ID.
     * 
     * @param id ID del proveedor a eliminar.
     * @throws RuntimeException si el proveedor no existe.
     */
    public void eliminarProveedor(Long idUsuario) {
        Proveedor proveedor = proveedorRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedorRepository.delete(proveedor);
    }
    
    /**
     * Convierte una entidad {@link Proveedor} a un {@link ProveedorResponseDTO}.
     * 
     * @param proveedor Entidad a convertir.
     * @return DTO de respuesta con los datos del proveedor.
     */
    private ProveedorResponseDTO mapearResponseDTO(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombre(proveedor.getNombre());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());
        dto.setFechaRegistro(proveedor.getFechaRegistro());
        dto.setUrl(proveedor.getUrl());
        
        Usuario usuario = proveedor.getUsuario();
        if (usuario != null) {
            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setRol(usuario.getRol());
            usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
            dto.setUsuario(usuarioDTO);
        }
        return dto;
    }
}