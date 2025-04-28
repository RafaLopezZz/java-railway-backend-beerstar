package com.tfc.beerstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.beerstar.model.Pago;
import com.tfc.beerstar.model.Pedido;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    /**
     * Recupera el pago asociado a un pedido, si existe.
     */
    Optional<Pago> findByPedido(Pedido pedido);

    /**
     * Ejemplo de consulta derivada más compleja: busca pagos por método y estado.
     */
    List<Pago> findByMetodoPagoAndEstadoPago(String metodo, String estado);
}
