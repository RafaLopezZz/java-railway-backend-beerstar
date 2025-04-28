package com.tfc.beerstar.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
      // Buscar cliente por nombre
    List<Cliente> findByNombre(String nombre);

    // Buscar cliente por usuario
    Optional<Cliente> findByUsuario_IdUsuario(Long idUsuario);
    Optional<Cliente> findByUsuario(Usuario usuario);

    // Buscar clientes registrados después de una fecha específica
    List<Cliente> findByFechaRegistroAfter(LocalDateTime fecha);

    // Buscar clientes por teléfono
    Optional<Cliente> findByTelefono(String telefono);

}
