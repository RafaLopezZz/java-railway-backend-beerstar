package com.tfc.beerstar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Usuario;

/**
 * Repositorio para la entidad {@link Cliente}.
 *
 * <p>
 * Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository}, así
 * como consultas personalizadas para buscar clientes por diferentes criterios,
 * como nombre, usuario asociado, fecha de registro o teléfono.</p>
 * 
 * @author rafalopezzz
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca todos los clientes cuyo nombre coincida exactamente con el
     * proporcionado.
     *
     * @param nombre Nombre del cliente a buscar.
     * @return Lista de clientes con el nombre indicado.
     */
    List<Cliente> findByNombre(String nombre);

    /**
     * Busca el cliente asociado al usuario cuya entidad
     * {@link Usuario#getIdUsuario()} coincide con el ID proporcionado.
     *
     * @param idUsuario ID del usuario asociado al cliente.
     * @return {@link Optional} con el cliente si existe, o vacío si no se
     * encuentra.
     */
    Optional<Cliente> findByUsuario_IdUsuario(Long idUsuario);

    /**
     * Busca el cliente asociado a la entidad {@link Usuario} proporcionada.
     *
     * @param usuario Entidad {@link Usuario} asociada al cliente.
     * @return {@link Optional} con el cliente si existe, o vacío si no se
     * encuentra.
     */
    Optional<Cliente> findByUsuario(Usuario usuario);

    /**
     * Obtiene los clientes que se registraron después de la fecha/hora
     * indicada.
     *
     * @param fecha Fecha y hora límite.
     * @return Lista de clientes registrados posterior a la fecha proporcionada.
     */
    List<Cliente> findByFechaRegistroAfter(LocalDateTime fecha);

    /**
     * Busca el cliente cuyo teléfono coincide con el proporcionado.
     *
     * @param telefono Teléfono del cliente a buscar.
     * @return {@link Optional} con el cliente si existe, o vacío si no se
     * encuentra.
     */
    Optional<Cliente> findByTelefono(String telefono);

}
