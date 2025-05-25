package com.rlp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlp.ecommerce.model.DetalleOvp;

/**
 * Repositorio para acceder a los datos de la entidad DetalleOvp.
 * 
 * Permite realizar operaciones CRUD sobre los detalles de órdenes de venta a proveedores (OVP).
 * 
 * Aquí se pueden definir métodos personalizados para consultar por orden específica,
 * artículo, o cualquier combinación útil para el negocio.
 */
public interface DetalleOvpRepository extends JpaRepository<DetalleOvp, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar detalles de OVP por ID de OVP o por ID de artículo

}
