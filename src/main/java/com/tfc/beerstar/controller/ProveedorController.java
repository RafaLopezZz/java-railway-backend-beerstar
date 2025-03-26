package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ProveedorRequestDTO;
import com.tfc.beerstar.dto.response.ProveedorResponseDTO;
import com.tfc.beerstar.service.ProveedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/beerstar/usuarios/proveedores")
public class ProveedorController {

        @Autowired
    private ProveedorService proveedorService;

    @PostMapping("/registroProveedor")
    public ResponseEntity<ProveedorResponseDTO> crearProveedor(@Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorResponseDTO response = proveedorService.crearProveedor(proveedorRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/obtenerProveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedor(@PathVariable("id") Long id) {
        ProveedorResponseDTO response = proveedorService.obtenerProveedorPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarProveedores")
    public ResponseEntity<List<ProveedorResponseDTO>> listarProveedores() {
        List<ProveedorResponseDTO> lista = proveedorService.listarProveedores();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/actualizarProveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(@PathVariable("id") Long id,
                                                                      @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        ProveedorResponseDTO response = proveedorService.actualizarProveedor(id, proveedorRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminarProveedor/{id}")
    public ResponseEntity<String> eliminarProveedor(@PathVariable("id") Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.ok("Proveedor eliminado correctamente");
    }

}
