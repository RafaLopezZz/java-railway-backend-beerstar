package com.rlp.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlp.ecommerce.model.OrdenVentaProveedor;

/**
 * Repositorio de acceso a datos para la entidad OrdenVentaProveedor.
 * 
 * Permite realizar operaciones CRUD y consultas personalizadas sobre las órdenes
 * que se generan hacia proveedores a partir de pedidos de clientes.
 */
public interface OrdenVentaProveedorRepository extends JpaRepository<OrdenVentaProveedor, Long> {

    /**
     * Busca órdenes de venta por proveedor.
     */
    List<OrdenVentaProveedor> findByProveedor_IdProveedorOrderByFechaCreacionDesc(Long idProveedor);
    
    /**
     * Busca órdenes de venta por pedido.
     */
    List<OrdenVentaProveedor> findByPedido_IdPedido(Long idPedido);
    
    /**
     * Busca órdenes por estado.
     */
    List<OrdenVentaProveedor> findByEstadoOrderByFechaCreacionDesc(String estado);
    
    /**
     * Busca por número de orden.
     */
    Optional<OrdenVentaProveedor> findByNumeroOrden(String numeroOrden);
    
    /**
     * Cuenta órdenes por fecha para generar número único.
     */
    long countByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
}
