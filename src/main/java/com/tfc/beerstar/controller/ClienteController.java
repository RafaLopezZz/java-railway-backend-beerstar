package com.tfc.beerstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfc.beerstar.dto.request.ClienteRequestDTO;
import com.tfc.beerstar.dto.response.ClienteResponseDTO;
import com.tfc.beerstar.service.ClienteService;

/**
 * Controlador REST para la gestión de datos de clientes asociados a usuarios.
 *
 * <p>Proporciona endpoints para obtener y actualizar la información del cliente
 * correspondiente a un usuario determinado.</p>
 *
 * <p>Se habilita CORS con {@code @CrossOrigin(origins = "*")} para permitir peticiones
 * desde cualquier origen.</p>
 *
 * Endpoints disponibles:
 * <ul>
 *   <li>GET  /beerstar/usuarios/clientes  → Listar todos los clientes</li>
 *   <li>GET  /beerstar/usuarios/clientes/{idUsuario}  → Obtener datos de cliente por ID de usuario</li>
 *   <li>PUT  /beerstar/usuarios/clientes/{idCliente}  → Actualizar datos de cliente por ID de cliente</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beerstar/usuarios/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Obtiene los datos del cliente asociado al usuario indicado por su ID.
     *
     * @param idUsuario ID del usuario cuyo cliente se desea obtener.
     * @return {@code ResponseEntity<ClienteResponseDTO>} con los datos del cliente y HTTP 200.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorUsuarioId(@PathVariable Long idUsuario) {
        ClienteResponseDTO cliente = clienteService.obtenerClientePorUsuarioId(idUsuario);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Lista todos los clientes registrados en el sistema.
     *
     * @return {@code ResponseEntity<List<ClienteResponseDTO>>} con la lista de clientes y HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * Actualiza los datos del cliente indicado por su ID.
     *
     * @param idCliente ID del cliente a actualizar.
     * @param clienteRequestDTO DTO con los nuevos datos del cliente.
     * @return {@code ResponseEntity<ClienteResponseDTO>} con los datos actualizados del cliente y HTTP 200.
     */
    @PutMapping("/{idUsuario}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long idUsuario,
            @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(idUsuario, clienteRequestDTO);
        return ResponseEntity.ok(clienteActualizado);
    }
}
