package com.tfc.beerstar.model;


import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pago", schema = "beerstar_schema")
public class Pago {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;    // Tarjeta, Transferencia…

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago;     // COMPLETADO, FALLIDO…

    @Column(name = "fecha_pago", nullable = false, updatable = false)
    private Instant fechaPago = Instant.now();

    @Column(name = "transaccion_id")
    private String transaccionId;

}
