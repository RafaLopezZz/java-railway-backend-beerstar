package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * Controlador REST para la gestión administrativa de usuarios en Beerstar.
 *
 * <p>Proporciona endpoints para operaciones CRUD de usuarios, accesibles solo para administradores.
 * Utiliza {@link UsuarioService} para delegar la lógica de negocio.</p>
 *
 * <p>Características principales:</p>
 * <ul>
 *   <li>Habilita CORS para todos los orígenes</li>
 *   <li>Requiere autenticación para todas las operaciones</li>
 *   <li>Algunos endpoints requieren rol ADMIN (especificado en cada método)</li>
 * </ul>
 *
 * <p>Endpoints disponibles:</p>
 * <ul>
 *   <li>POST   /beerstar/usuarios/admin → Registrar nuevo usuario (requiere rol ADMIN)</li>
 *   <li>GET    /beerstar/usuarios/{idUsuario} → Obtener usuario por ID</li>
 *   <li>GET    /beerstar/usuarios → Listar todos los usuarios</li>
 *   <li>PUT    /beerstar/usuarios/{idUsuario} → Actualizar usuario existente</li>
 *   <li>DELETE /beerstar/usuarios/{idUsuario} → Eliminar usuario por ID</li>
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
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param idUsuario ID del usuario a obtener.
     * @return {@code ResponseEntity<UsuarioResponseDTO>} con los datos del usuario encontrado y HTTP 200.
     */
    @GetMapping("/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable("idUsuario") Long idUsuario) {
        UsuarioResponseDTO response = usuarioService.obtenerUsuarioPorId(idUsuario);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return {@code ResponseEntity<List<UsuarioResponseDTO>>} con la lista de usuarios y HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }


    /**
     * Actualiza un usuario existente.
     *
     * @param idUsuario ID del usuario a actualizar.
     * @param usuarioRequestDTO DTO con los nuevos datos del usuario. Debe pasar las validaciones de {@link Valid}.
     * @return {@code ResponseEntity<UsuarioResponseDTO>} con los datos del usuario actualizado y HTTP 200.
     */
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable("idUsuario") Long idUsuario,
                                                                  @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO response = usuarioService.actualizarUsuario(idUsuario, usuarioRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUsuario ID del usuario a eliminar.
     * @return {@code ResponseEntity<String>} con un mensaje de éxito y HTTP 200.
     */
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}