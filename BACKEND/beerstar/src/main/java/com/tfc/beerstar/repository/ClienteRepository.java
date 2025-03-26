package com.tfc.beerstar.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
      // Buscar cliente por nombre
    List<Cliente> findByNombre(String nombre);

    // Buscar cliente por usuario
    Optional<Cliente> findByUsuario(Usuario usuario);

    // Buscar clientes registrados después de una fecha específica
    List<Cliente> findByFechaRegistroAfter(LocalDateTime fecha);

    // Buscar clientes por teléfono
    Optional<Cliente> findByTelefono(String telefono);

}
