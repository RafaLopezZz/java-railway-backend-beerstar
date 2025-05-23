package com.tfc.beerstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.beerstar.model.Cliente;
import com.tfc.beerstar.model.Pedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Lista todos los pedidos de un cliente.
     */
    List<Pedido> findByCliente(Cliente cliente);

    /**
     * Lista paginada de pedidos de un cliente, útil para historiales muy largos.
     */
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);

    Optional<Pedido> findByCliente_Usuario_IdUsuario(Long idUsuario);
}
