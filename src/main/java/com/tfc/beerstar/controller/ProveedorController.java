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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/usuarios/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorUsuarioId(@PathVariable Long usuarioId) {
        ProveedorResponseDTO proveedor = proveedorService.obtenerProveedorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/listarProveedores")
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        List<ProveedorResponseDTO> proveedores = proveedorService.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }
    
    @PutMapping("/{proveedorId}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(
            @PathVariable Long proveedorId,
            @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorResponseDTO proveedorActualizado = proveedorService.actualizarProveedor(proveedorId, proveedorRequestDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }

}
