package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/**
 * Controlador REST para la gestión de usuarios en Beerstar.
 *
 * <p>Proporciona endpoints para registrar, obtener, listar, actualizar y eliminar usuarios.
 * Utiliza {@link UsuarioService} para delegar la lógica de negocio.</p>
 *
 * <p>Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir peticiones
 * desde cualquier origen.</p>
 *
 * Endpoints disponibles:
 * <ul>
 *   <li>POST   /beerstar/usuarios/registro               → Registrar nuevo usuario</li>
 *   <li>GET    /beerstar/usuarios/obtenerUsuario/{id}    → Obtener usuario por ID</li>
 *   <li>GET    /beerstar/usuarios/listarUsuarios         → Listar todos los usuarios</li>
 *   <li>PUT    /beerstar/usuarios/actualizarUsuario/{id} → Actualizar usuario existente</li>
 *   <li>DELETE /beerstar/usuarios/eliminarUsuario/{id}   → Eliminar usuario por ID</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuarioRequestDTO DTO con los datos del usuario a registrar. Debe pasar las validaciones de {@link Valid}.
     * @return {@code ResponseEntity<UsuarioResponseDTO>} con los datos del usuario creado y HTTP 200.
     */
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a obtener.
     * @return {@code ResponseEntity<UsuarioResponseDTO>} con los datos del usuario encontrado y HTTP 200.
     */
    @GetMapping("/obtenerUsuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable("id") Long id) {
        UsuarioResponseDTO response = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return {@code ResponseEntity<List<UsuarioResponseDTO>>} con la lista de usuarios y HTTP 200.
     */
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }


    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar.
     * @param usuarioRequestDTO DTO con los nuevos datos del usuario. Debe pasar las validaciones de {@link Valid}.
     * @return {@code ResponseEntity<UsuarioResponseDTO>} con los datos del usuario actualizado y HTTP 200.
     */
    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable("id") Long id,
                                                                  @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.actualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP 200.
     */
    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}