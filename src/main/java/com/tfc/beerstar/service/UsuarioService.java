package com.tfc.beerstar.service;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.UsuarioRequestDTO;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.exception.EmailAlreadyExistsException;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO uDto) {

        String email = uDto.getEmail().trim().toLowerCase();

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(uDto.getPassword()));
        usuario.setRol("USER");
        usuario.setTipoUsuario("CLIENTE");

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapearResponseDTO(usuarioGuardado);
    }

        public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return mapearResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO uDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        // Actualizar campos (evitando por ejemplo la actualización de la contraseña si así se requiere)
        usuario.setEmail(uDto.getEmail());
        usuario.setPassword(uDto.getPassword());
        uDto.getRol();
        uDto.getTipoUsuario();
       
        
        Usuario actualizado = usuarioRepository.save(usuario);
        return mapearResponseDTO(actualizado);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }


    private UsuarioResponseDTO mapearResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        return dto;
    }
}
