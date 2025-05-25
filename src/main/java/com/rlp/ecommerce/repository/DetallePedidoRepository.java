package com.rlp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlp.ecommerce.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoIdPedido(Long pedidoId);
    List<DetallePedido> findByArticuloIdArticulo(Long articuloId);

}
