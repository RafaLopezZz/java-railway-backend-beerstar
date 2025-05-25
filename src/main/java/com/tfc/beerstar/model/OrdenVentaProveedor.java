package com.tfc.beerstar.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representa una orden de venta que se envía a un proveedor, generada a partir
 * de un pedido de un cliente.
 *
 * Esta clase sirve para gestionar las relaciones entre los pedidos de los
 * clientes y los proveedores que deben abastecer parte o la totalidad de esos
 * pedidos.
 *
 * Mapeada a la tabla "orden_venta_proveedor" en el esquema "beerstar_schema".
 */
@Data
@Entity
@Table(name = "orden_venta_proveedor", schema = "beerstar_schema")
public class OrdenVentaProveedor {

    /**
     * Identificador único de la orden de venta. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOvp;

    /**
     * Pedido del cliente asociado a esta orden de venta. Se establece una
     * relación de muchos a uno con la entidad Pedido.
     */
    @ManyToOne
    @JoinColumn(name = "id_pedido_cliente")
    private Pedido pedidoCliente;

    /**
     * Proveedor al que se envía esta orden de venta. Se establece una relación
     * de muchos a uno con la entidad Proveedor.
     */
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    /**
     * Pedido del proveedor asociado a esta orden de venta. Se establece una
     * relación de muchos a uno con la entidad Pedido, que representa el pedido
     * realizado al proveedor.
     */
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    /**
     * Número único de la orden de venta (formato: OVP-YYYYMMDD-XXXX).
     */
    @Column(unique = true)
    private String numeroOrden;

    /**
     * Fecha y hora en que se creó la orden de venta. Se establece un valor por
     * defecto de la fecha y hora actual.
     */
    private Instant fechaCreacion;

    /**
     * Estado actual de la orden de venta. Puede ser PENDIENTE, ENVIADO,
     * ENTREGADO o CANCELADO.
     */
    private String estado;

    /**
     * Lista de líneas o ítems que forman parte de esta orden de venta. Cada
     * línea indica qué artículo y cuánta cantidad se le solicita al proveedor.
     *
     * - CascadeType.ALL: cualquier cambio en la orden (guardar, eliminar) se
     * propaga a las líneas. - orphanRemoval = true: si se elimina una línea de
     * la lista, también se elimina de la base.
     */
    @OneToMany(mappedBy = "ordenVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOvp> lineas = new ArrayList<>();

    /**
     * Observaciones adicionales para el proveedor.
     */
    private String observaciones;
}
