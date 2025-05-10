package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.OrdenVentaProveedor;

/**
 * Repositorio de acceso a datos para la entidad OrdenVentaProveedor.
 * 
 * Permite realizar operaciones CRUD y consultas personalizadas sobre las órdenes
 * que se generan hacia proveedores a partir de pedidos de clientes.
 */
public interface OrdenVentaProveedorRepository extends JpaRepository<OrdenVentaProveedor, Long> {

    /**
     * Busca todas las órdenes de venta asociadas a un proveedor específico.
     * 
     * @param idProveedor ID del proveedor.
     * @return Lista de órdenes de venta relacionadas con el proveedor.
     */
    List<OrdenVentaProveedor> findByProveedor_IdProveedor(Long idProveedor);

}
