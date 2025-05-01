package com.tfc.beerstar.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfc.beerstar.dto.request.ClienteRequestDTO;
import com.tfc.beerstar.dto.response.ClienteResponseDTO;
import com.tfc.beerstar.dto.response.UsuarioResponseDTO;
import com.tfc.beerstar.exception.ResourceNotFoundException;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


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

    public ClienteResponseDTO obtenerClientePorUsuarioId(Long usuarioId) {
        Cliente cliente = clienteRepository.findByUsuario_IdUsuario(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado para el usuario con ID: " + usuarioId));

        return mapearResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapearResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO actualizarCliente(Long clienteId, ClienteRequestDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));

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

    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

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
