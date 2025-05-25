package com.rlp.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlp.ecommerce.dto.request.ClienteRequestDTO;
import com.rlp.ecommerce.dto.response.ClienteResponseDTO;
import com.rlp.ecommerce.dto.response.UsuarioResponseDTO;
import com.rlp.ecommerce.exception.ResourceNotFoundException;
import com.rlp.ecommerce.model.Cliente;
import com.rlp.ecommerce.model.Usuario;
import com.rlp.ecommerce.repository.ClienteRepository;
import com.rlp.ecommerce.repository.UsuarioRepository;

/**
 * Servicio que encapsula la lógica de negocio para la gestión de clientes.
 * 
 * <p>Permite crear, obtener, listar, actualizar y eliminar clientes, así como
 * mapear entre entidades JPA y DTOs de petición/respuesta.</p>
 * 
 * <p>Se apoya en {@link ClienteRepository} para el acceso a datos y lanza
 * {@link ResourceNotFoundException} cuando no se encuentran recursos.</p>
 * 
 * @author rafalopezzz
 */
@Service
public class ClienteService {

    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo cliente asociado a un {@link Usuario}.
     * 
     * <p>Si el DTO es nulo, se inicializa por defecto, y se registran
     * los datos de nombre, teléfono y dirección, junto con la fecha actual.</p>
     * 
     * @param usuario Entidad de usuario asociada al cliente.
     * @param cDto    DTO de petición con los datos del cliente; puede ser nulo.
     */
    public void crearCliente(Usuario usuario, ClienteRequestDTO cDto) {
        if (cDto == null) {
            cDto = new ClienteRequestDTO(); // Inicializar el DTO si es nulo
        }

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setNombre(cDto.getNombre());
        cliente.setTelefono(cDto.getTelefono());
        cliente.setDireccion(cDto.getDireccion());
        cliente.setFechaRegistro(LocalDateTime.now());

        clienteRepository.save(cliente);
    }

    /**
     * Obtiene un cliente a partir del ID de usuario asociado.
     * 
     * @param usuarioId ID del usuario cuya entidad Cliente se desea recuperar.
     * @return DTO de respuesta con los datos del cliente encontrado.
     * @throws ResourceNotFoundException si no existe un cliente para el usuario dado.
     */
    public ClienteResponseDTO obtenerClientePorUsuarioId(Long idUsuario) {
        Cliente cliente = clienteRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado para el usuario con ID: " + idUsuario));

        return mapearResponseDTO(cliente);
    }

    /**
     * Obtiene un cliente a partir de su ID.
     * 
     * @param clienteId ID del cliente a recuperar.
     * @return DTO de respuesta con los datos del cliente encontrado.
     * @throws ResourceNotFoundException si no existe un cliente con el ID dado.
     */
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param clienteId ID del cliente a actualizar.
     * @param clienteDTO DTO de petición con los nuevos datos del cliente.
     * @return DTO de respuesta con los datos del cliente actualizado.
     * @throws ResourceNotFoundException si no existe un cliente con el ID dado.
     */
    public ClienteResponseDTO actualizarCliente(Long idUsuario, ClienteRequestDTO clienteDTO) {
        Cliente cliente = clienteRepository.findByUsuario_IdUsuario(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + idUsuario));

        // Actualizar los campos
        if (clienteDTO.getNombre() != null)
            cliente.setNombre(clienteDTO.getNombre());
        if (clienteDTO.getTelefono() != null)
            cliente.setTelefono(clienteDTO.getTelefono());
        if (clienteDTO.getDireccion() != null)
            cliente.setDireccion(clienteDTO.getDireccion());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return mapearResponseDTO(clienteActualizado);
    }

    /**
     * Elimina un cliente por su ID.
     * 
     * @param id ID del cliente a eliminar.
     * @throws ResourceNotFoundException si no existe un cliente con ese ID.
     */
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    public Cliente findByEmail(String email) {
        // 1. Primero buscar el Usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        
        // 2. Verificar que el usuario es de tipo CLIENTE
        if (!"CLIENTE".equals(usuario.getTipoUsuario())) {
            throw new ResourceNotFoundException("El usuario no es un cliente");
        }
        
        // 3. Obtener el Cliente asociado al Usuario
        if (usuario.getCliente() == null) {
            throw new ResourceNotFoundException("No existe un cliente asociado al usuario con email: " + email);
        }
        
        return usuario.getCliente();
    }


    /**
     * Mapea una entidad {@link Cliente} a un DTO de respuesta {@link ClienteResponseDTO}.
     * 
     * @param cliente Entidad cliente a mapear.
     * @return DTO de respuesta con los datos del cliente.
     */
    private ClienteResponseDTO mapearResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        
        // Mapear el usuario asociado
        Usuario usuario = cliente.getUsuario();
        if (usuario != null) {
            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setRol(usuario.getRol());
            usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
            dto.setUsuario(usuarioDTO);
        }
        return dto;
    }
}
