package com.tfc.beerstar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.beerstar.model.Carrito;
import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Pedido;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    Optional<Carrito> findByCliente(Cliente cliente);

    /**
     * Lista paginada de pedidos de un cliente, útil para historiales muy largos.
     */
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);

    void deleteByCliente(Cliente cliente);
}
