package com.rlp.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rlp.ecommerce.model.Carrito;
import com.rlp.ecommerce.model.Cliente;
import com.rlp.ecommerce.model.Pedido;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    @Query("""
  SELECT c FROM Carrito c
  LEFT JOIN FETCH c.detalleList
  WHERE c.cliente = :cliente AND c.finalizado = FALSE
  """)
    Optional<Carrito> findActivoConDetalles(@Param("cliente") Cliente cliente);

    @Query("""
  SELECT c FROM Carrito c
  LEFT JOIN FETCH c.detalleList
  WHERE c.cliente = :cliente
  """)
    Optional<Carrito> findByClienteConDetalles(@Param("cliente") Cliente cliente);

    Optional<Carrito> findByClienteAndFinalizadoFalse(Cliente cliente);

    Optional<Carrito> findByCliente(Cliente cliente);

    /**
     * Lista paginada de pedidos de un cliente, útil para historiales muy
     * largos.
     */
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);

    void deleteByCliente(Cliente cliente);
}
