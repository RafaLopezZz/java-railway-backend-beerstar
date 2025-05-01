package com.tfc.beerstar.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.ProveedorRequestDTO;
import com.tfc.beerstar.dto.response.ProveedorResponseDTO;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Proveedor;
import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.ProveedorRepository;

@Service
public class ProveedorService {

    
    @Autowired
    private ProveedorRepository proveedorRepository;


    public ProveedorResponseDTO crearProveedor(Usuario usuario, ProveedorRequestDTO pDto) {
        if (pDto == null) {
            pDto = new ProveedorRequestDTO(); // Valores por defecto si no se proporcionan
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setUsuario(usuario);
        proveedor.setNombre(pDto.getNombre());
        proveedor.setDireccion(pDto.getDireccion());
        proveedor.setTelefono(pDto.getTelefono());
        proveedor.setFechaRegistro(LocalDateTime.now());

        Proveedor guardado = proveedorRepository.save(proveedor);
        return mapearResponseDTO(guardado);
    }

    public ProveedorResponseDTO obtenerProveedorPorUsuarioId(Long usuarioId) {
        Proveedor proveedor = proveedorRepository.findByUsuario_IdUsuario(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado para el usuario con ID: " + usuarioId));
        
        return mapearResponseDTO(proveedor);
    }

    public List<ProveedorResponseDTO> listarProveedores() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public ProveedorResponseDTO actualizarProveedor(Long id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        
        Proveedor actualizado = proveedorRepository.save(proveedor);
        return mapearResponseDTO(actualizado);
    }

    public void eliminarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedorRepository.delete(proveedor);
    }
    
    private ProveedorResponseDTO mapearResponseDTO(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setIdProveedor(proveedor.getId_proveedor());
        dto.setNombre(proveedor.getNombre());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());
        dto.setFechaRegistro(proveedor.getFechaRegistro());
        
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
