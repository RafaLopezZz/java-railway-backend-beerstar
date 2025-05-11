package com.tfc.beerstar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.LoginRequest;
import com.tfc.beerstar.dto.request.UsuarioRequestDTO;
import com.tfc.beerstar.dto.response.JwtResponse;
import com.tfc.beerstar.dto.response.MessageResponse;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.exception.EmailAlreadyExistsException;
import com.tfc.beerstar.security.JwtUtils;
import com.tfc.beerstar.security.UserDetailsImpl;
import com.tfc.beerstar.service.UsuarioService;

import jakarta.validation.Valid;

/**
 * Controlador de autenticación para usuarios públicos (clientes y proveedores).
 * <p>
 * Este controlador maneja los endpoints de acceso público que no requieren
 * autenticación previa: inicio de sesión y registro diferenciado por tipo de
 * usuario. Implementa JWT para la autenticación de usuarios.
 * </p>
 *
 * <p>
 * Endpoints disponibles:</p>
 * <ul>
 * <li>POST /beerstar/auth/login → Autenticación y generación de token JWT</li>
 * <li>POST /beerstar/auth/registro/cliente → Registro exclusivo para
 * clientes</li>
 * <li>POST /beerstar/auth/registro/proveedor → Registro exclusivo para
 * proveedores</li>
 * </ul>
 *
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Autentica un usuario y genera un token JWT.
     * <p>
     * Proceso:
     * <ol>
     * <li>Valida las credenciales del usuario contra la base de datos</li>
     * <li>Genera un token JWT si las credenciales son válidas</li>
     * <li>Devuelve el token junto con información básica del usuario</li>
     * </ol>
     * </p>
     *
     * @param loginRequest DTO con email y password del usuario
     * @return ResponseEntity con JWT e información del usuario autenticado
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Autenticar credenciales del usuario
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Establecer autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generar token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Obtener detalles del usuario autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Devolver respuesta con el token y detalles del usuario
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getTipoUsuario(),
                roles));
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * <p>
     * Este endpoint está optimizado para el registro público de clientes. El
     * tipo de usuario se establece automáticamente como "CLIENTE".
     * </p>
     *
     * @param usuarioRequestDTO DTO con datos del usuario y cliente a registrar
     * @return ResponseEntity con mensaje de éxito (200) o error (400)
     * @throws EmailAlreadyExistsException Si el email ya está registrado
     */
    @PostMapping("/registro/cliente")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            // Forzar tipo CLIENTE independientemente de lo que envíe el usuario
            usuarioRequestDTO.setTipoUsuario("CLIENTE");
            usuarioRequestDTO.setRol("USER");
            UsuarioResponseDTO response = usuarioService.crearUsuario(usuarioRequestDTO);
            return ResponseEntity.ok(response);

        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest()
                    .body("Error: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error en el registro: " + ex.getMessage()));
        }
    }

    /**
     * Registra un nuevo proveedor en el sistema.
     * <p>
     * Este endpoint está optimizado para el registro público de proveedores. El
     * tipo de usuario se establece automáticamente como "PROVEEDOR".
     * </p>
     *
     * @param usuarioRequestDTO DTO con datos del usuario y proveedor a registrar
     * @return ResponseEntity con mensaje de éxito (200) o error (400)
     * @throws EmailAlreadyExistsException Si el email ya está registrado
     */
    @PostMapping("/registro/proveedor")
    public ResponseEntity<?> registrarProveedor(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            // Forzar tipo PROVEEDOR independientemente de lo que envíe el usuario
            usuarioRequestDTO.setTipoUsuario("PROVEEDOR");

            // Forzar rol USER para registros públicos
            usuarioRequestDTO.setRol("USER");
            UsuarioResponseDTO response = usuarioService.crearUsuario(usuarioRequestDTO);
            return ResponseEntity.ok(response);

        } catch (EmailAlreadyExistsException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: " + ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error en el registro: " + ex.getMessage()));
        }
    }
}