package com.rlp.ecommerce.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlp.ecommerce.model.Articulos;
import com.rlp.ecommerce.model.DetalleOvp;
import com.rlp.ecommerce.model.DetallePedido;
import com.rlp.ecommerce.model.OrdenVentaProveedor;
import com.rlp.ecommerce.model.Pedido;
import com.rlp.ecommerce.model.Proveedor;
import com.rlp.ecommerce.repository.DetalleOvpRepository;
import com.rlp.ecommerce.repository.OrdenVentaProveedorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio para gestionar órdenes de venta a proveedores.
 * 
 * <p>Maneja la creación automática de OVPs cuando se procesan pedidos,
 * así como la consulta y actualización de estados de las órdenes.</p>
 */
@Service
@Slf4j
public class OrdenVentaProveedorService {

 @Autowired
    private OrdenVentaProveedorRepository ordenVentaProveedorRepository;

    @Autowired
    private DetalleOvpRepository detalleOvpRepository;


    /**
     * Genera órdenes de venta a proveedores a partir de un pedido.
     * 
     * <p>Este método agrupa los detalles del pedido por proveedor y crea una
     * orden de venta para cada proveedor con los artículos correspondientes.</p>
     * 
     * @param pedido El pedido del cual se generarán las órdenes de venta.
     */
    @Transactional
    public void generarOrdenesVentaDesdePedido(Pedido pedido) {

        // Map<Proveedor, List<DetallePedido>>
        Map<Proveedor, List<DetallePedido>> detallesPorProveedor = new HashMap<>();

        for (DetallePedido detalle : pedido.getDetallePedido()) {
            Articulos articulo = detalle.getArticulo();
            Proveedor proveedor = articulo.getProveedor();

            detallesPorProveedor
                .computeIfAbsent(proveedor, k -> new ArrayList<>())
                .add(detalle);
        }

        for (Map.Entry<Proveedor, List<DetallePedido>> entry : detallesPorProveedor.entrySet()) {
            Proveedor proveedor = entry.getKey();
            List<DetallePedido> detalles = entry.getValue();

            OrdenVentaProveedor ovp = new OrdenVentaProveedor();
            ovp.setProveedor(proveedor);
            ovp.setFechaCreacion(Instant.now());
            ovp = ordenVentaProveedorRepository.save(ovp);

            for (DetallePedido detalle : detalles) {
                DetalleOvp detalleOvp = new DetalleOvp();
                detalleOvp.setOrdenVenta(ovp);
                detalleOvp.setArticulos(detalle.getArticulo());
                detalleOvp.setCantidad(detalle.getCantidad());
                detalleOvp.setPrecioUnitario(detalle.getArticulo().getPrecio()); // O de proveedor
                detalleOvpRepository.save(detalleOvp);
            }
        }
    }
}

