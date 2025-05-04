package com.tfc.beerstar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.LoginRequest;
import com.tfc.beerstar.dto.request.SignUpRequest;
import com.tfc.beerstar.dto.response.JwtResponse;
import com.tfc.beerstar.dto.response.MessageResponse;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Proveedor;
import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.UsuarioRepository;
import com.tfc.beerstar.security.JwtUtils;
import com.tfc.beerstar.security.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, 
                                                 userDetails.getId(), 
                                                 userDetails.getEmail(),
                                                 userDetails.getTipoUsuario(), 
                                                 roles));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Este email ya está registrado"));
        }

        // Crear una nueva cuenta de usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setPassword(encoder.encode(signUpRequest.getPassword()));
        usuario.setRol(signUpRequest.getRol());
        usuario.setTipoUsuario(signUpRequest.getTipoUsuario());
        
        // Asignar el tipo de usuario adecuado basado en la solicitud
        if (signUpRequest.getTipoUsuario().equals("CLIENTE")) {
            Cliente cliente = new Cliente();
            cliente.setNombre(signUpRequest.getNombreCliente());
            cliente.setTelefono(signUpRequest.getTelefonoCliente());
            cliente.setDireccion(signUpRequest.getDireccionCliente());
            cliente.setUsuario(usuario);
            usuario.setCliente(cliente);
        } else if (signUpRequest.getTipoUsuario().equals("PROVEEDOR")) {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(signUpRequest.getNombreEmpresa());
            proveedor.setTelefono(signUpRequest.getTelefonoEmpresa());
            proveedor.setDireccion(signUpRequest.getDireccionEmpresa());
            proveedor.setUsuario(usuario);
            usuario.setProveedor(proveedor);
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente"));
    }
}