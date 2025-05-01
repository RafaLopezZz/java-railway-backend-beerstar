package com.tfc.beerstar.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.tfc.beerstar.model.Proveedor;
import com.tfc.beerstar.model.Usuario;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

        // Buscar proveedor por nombre
    List<Proveedor> findByNombre(String nombre);

    // Buscar proveedor por usuario
    Optional<Proveedor> findByUsuario_IdUsuario(Long idUsuario);
    Optional<Proveedor> findByUsuario(Usuario usuario);

    // Buscar proveedores registrados después de una fecha específica
    List<Proveedor> findByFechaRegistroAfter(LocalDateTime fecha);

    // Buscar proveedor por teléfono
    Optional<Proveedor> findByTelefono(String telefono);

}
