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

import com.tfc.beerstar.dto.request.UsuarioRequestDTO;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/beerstar/usuarios")
public class UsuarioController {

     @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Obtener un usuario por ID
    @GetMapping("/obtenerUsuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable("id") Long id) {
        UsuarioResponseDTO response = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    // Listar todos los usuarios
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    // Actualizar un usuario (se puede decidir si se permite actualizar ciertos campos)
    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable("id") Long id,
                                                                  @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.actualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Eliminar un usuario
    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}
