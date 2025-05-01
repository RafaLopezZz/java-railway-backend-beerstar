package com.tfc.beerstar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Proveedor;
import com.tfc.beerstar.model.Usuario;

/**
 * Repositorio para la entidad {@link Proveedor}.
 *
 * <p>Proporciona operaciones CRUD básicas heredadas de {@link JpaRepository},
 * así como consultas personalizadas para buscar proveedores por diferentes criterios,
 * como nombre, usuario asociado, fecha de registro o teléfono.</p>
 * 
 * @author rafalopezzz
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Busca todos los proveedores cuyo nombre coincida exactamente con el proporcionado.
     *
     * @param nombre Nombre del proveedor a buscar.
     * @return Lista de proveedores con el nombre indicado.
     */
    List<Proveedor> findByNombre(String nombre);

    /**
     * Busca el proveedor asociado al usuario cuyo ID coincide con el proporcionado.
     *
     * @param idUsuario ID del usuario asociado al proveedor.
     * @return {@link Optional} con el proveedor si existe, o vacío si no se encuentra.
     */
    Optional<Proveedor> findByUsuario_IdUsuario(Long idUsuario);

    /**
     * Busca el proveedor asociado al usuario proporcionado.
     *
     * @param usuario Usuario asociado al proveedor.
     * @return {@link Optional} con el proveedor si existe, o vacío si no se encuentra.
     */
    Optional<Proveedor> findByUsuario(Usuario usuario);

    /**
     * Obtiene los proveedores que se registraron después de la fecha/hora indicada.
     *
     * @param fecha Fecha y hora límite.
     * @return Lista de proveedores registrados posterior a la fecha proporcionada.
     */
    List<Proveedor> findByFechaRegistroAfter(LocalDateTime fecha);
    
    /**
     * Busca el proveedor cuyo teléfono coincide con el proporcionado.
     *
     * @param telefono Teléfono del proveedor a buscar.
     * @return {@link Optional} con el proveedor si existe, o vacío si no se encuentra.
     */
    Optional<Proveedor> findByTelefono(String telefono);

}
