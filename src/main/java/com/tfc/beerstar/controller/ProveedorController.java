package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ProveedorRequestDTO;
import com.tfc.beerstar.dto.response.ProveedorResponseDTO;
import com.tfc.beerstar.service.ProveedorService;

/**
 * Controlador REST para manejar las operaciones relacionadas con los proveedores.
 * <p>
 * Permite crear, obtener, listar, actualizar y eliminar proveedores.
 * </p>
 * 
 * <p>Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir peticiones
 * desde cualquier origen.</p>
 *
 * Endpoints disponibles:
 * <ul>
 *   <li>GET  /beerstar/usuarios/proveedores/listarProveedores  → Listar todos los proveedores</li>
 *   <li>GET  /beerstar/usuarios/proveedores/{usuarioId}  → Obtener datos de proveedor por ID de usuario</li>
 *   <li>PUT  /beerstar/usuarios/proveedores/{proveedorId}  → Actualizar datos de proveedor por ID de cliente</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/usuarios/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene los datos de un proveedor asociado a un usuario por su ID.
     *
     * @param usuarioId ID del usuario asociado al proveedor.
     * @return ProveedorResponseDTO con los datos del proveedor.
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorUsuarioId(@PathVariable Long usuarioId) {
        ProveedorResponseDTO proveedor = proveedorService.obtenerProveedorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proveedor);
    }

    /**
     * Lista todos los proveedores registrados en el sistema.
     *
     * @return Lista de ProveedorResponseDTO con los datos de todos los proveedores.
     */
    @GetMapping("/listarProveedores")
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        List<ProveedorResponseDTO> proveedores = proveedorService.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }
    
    /**
     * Actualiza los datos de un proveedor por su ID.
     *
     * @param proveedorId ID del proveedor a actualizar.
     * @param proveedorRequestDTO DTO con los nuevos datos del proveedor.
     * @return ProveedorResponseDTO con los datos actualizados del proveedor.
     */
    @PutMapping("/{proveedorId}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(
            @PathVariable Long proveedorId,
            @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorResponseDTO proveedorActualizado = proveedorService.actualizarProveedor(proveedorId, proveedorRequestDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }

}
