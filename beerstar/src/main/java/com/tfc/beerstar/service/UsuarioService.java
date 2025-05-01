package com.tfc.beerstar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfc.beerstar.dto.request.UsuarioRequestDTO;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.exception.EmailAlreadyExistsException;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.UsuarioRepository;

/**
 * Servicio que encapsula la lógica de negocio para la gestión de usuarios.
 * 
 * <p>Permite crear, obtener, listar, actualizar y eliminar usuarios, así como
 * mapear entre entidades JPA y DTOs de petición/respuesta. Incluye validaciones
 * como comprobación de email duplicado y manejo de tipos de usuario.</p>
 * 
 * <p>En la creación de usuarios, delega la creación de registros en las tablas
 * correspondientes de Cliente o Proveedor según el tipo de usuario.</p>
 * 
 * @author rafalopezzz
 */
@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor con inyección de {@link PasswordEncoder}.
     * 
     * @param passwordEncoder Codificador de contraseñas (BCrypt).
     */
    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Crea un nuevo usuario en la base de datos.
     * 
     * <p>Valida que el email no exista previamente, asigna un rol por defecto,
     * codifica la contraseña y crea el registro adicional en Cliente o Proveedor
     * según el tipo de usuario.</p>
     * 
     * @param uDto DTO de petición con los datos del usuario a crear.
     * @return DTO de respuesta con los datos del usuario recién creado.
     * @throws EmailAlreadyExistsException si el email ya está registrado.
     * @throws IllegalArgumentException   si el tipo de usuario no es válido.
     */
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO uDto) {
        // Normalizar el email
        String email = uDto.getEmail().trim().toLowerCase();
        // Comprobar si el email ya existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("El email ya está en uso");
        }

        // Validación y asignación del tipo de usuario
        String tipoUsuario = uDto.getTipoUsuario();
        if (tipoUsuario == null) {
            tipoUsuario = "CLIENTE"; // Valor por defecto
        } else if (!tipoUsuario.equals("CLIENTE") && !tipoUsuario.equals("PROVEEDOR")) {
            throw new IllegalArgumentException("Tipo de usuario no válido. Debe ser CLIENTE o PROVEEDOR");
        }

        // Crear y guardar el usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(uDto.getPassword()));
        usuario.setRol("USER");
        usuario.setTipoUsuario(tipoUsuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Dependiendo del tipo, crea los registros en la tabla correspondiente
        if (tipoUsuario.equals("CLIENTE")) {
            clienteService.crearCliente(usuarioGuardado, uDto.getClienteData());
        } else if (tipoUsuario.equals("PROVEEDOR")) {
            proveedorService.crearProveedor(usuarioGuardado, uDto.getProveedorData());
        }

        return mapearResponseDTO(usuarioGuardado);
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id ID del usuario a buscar.
     * @return DTO de respuesta con los datos del usuario encontrado.
     * @throws ResourceNotFoundException si el usuario no se encuentra.
     */
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return mapearResponseDTO(usuario);
    }

    /**
     * Obtiene un usuario por su email.
     * 
     * @param email Email del usuario a buscar.
     * @return DTO de respuesta con los datos del usuario encontrado.
     * @throws ResourceNotFoundException si el usuario no se encuentra.
     */
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param id   ID del usuario a actualizar.
     * @param uDto DTO de petición con los nuevos datos del usuario.
     * @return DTO de respuesta con los datos del usuario actualizado.
     * @throws ResourceNotFoundException si el usuario no se encuentra.
     */
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

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id ID del usuario a eliminar.
     * @throws ResourceNotFoundException si el usuario no se encuentra.
     */
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    /**
     * Mapea un objeto {@link Usuario} a un {@link UsuarioResponseDTO}.
     * 
     * @param usuario Entidad JPA de usuario
     * @return DTO de respuesta con los datos del usuario.
     */
    private UsuarioResponseDTO mapearResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        return dto;
    }
}
