package com.tfc.beerstar.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.ToString;

@Data
@Entity
@Table(name = "pedido", schema = "beerstar_schema")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_pedido")
    private Instant fechaPedido = Instant.now();

    @Column(name = "estado_pedido")
    private String estadoPedido; // PENDIENTE, ENVIADO, ENTREGADO, CANCELADO

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detallePedido = new ArrayList<>(); // Relación con detalle_pedido

    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
    @Column(name = "iva")
    private BigDecimal iva;
    
    @Column(name = "gastos_envio")
    private BigDecimal gastosEnvio;
    
    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;    // Tarjeta, Transferencia…

    @Column(name = "id_transaccion")
    private String idTransaccion;
    
    // Método para recalcular totales
    public void recalcularTotales() {
        this.subtotal = detallePedido.stream()
            .map(DetallePedido::getTotalLinea)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        // El total incluye subtotal + impuestos + gastos de envío
        this.total = this.subtotal
            .add(this.iva != null ? this.iva : BigDecimal.ZERO)
            .add(this.gastosEnvio != null ? this.gastosEnvio : BigDecimal.ZERO);
    }
}
