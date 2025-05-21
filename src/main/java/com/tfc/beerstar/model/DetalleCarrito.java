package com.tfc.beerstar.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "detalle_carrito", schema = "beerstar_schema")
public class DetalleCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_carrito")
    private Long idDetalleCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_articulo", nullable = true)
    private Articulos articulos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote", nullable = true)
    private Lotes lote;

    @Column(name = "cantidad", nullable = true)
    private int cantidad;

    @Column(name = "cantidad_articulos", nullable = true)
    private Integer cantidadArticulos = 0;

    @Column(name = "cantidad_lotes", nullable = true)
    private Integer cantidadLotes = 0;

    @Column(name = "precio_unitario", nullable = true)
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Column(name = "total_linea", nullable = true)
    private BigDecimal totalLinea = BigDecimal.ZERO;

// Método para calcular el total de línea
    public void calcularTotalLinea() {
        if (this.precioUnitario != null && this.cantidad > 0) {
            this.totalLinea = this.precioUnitario.multiply(new BigDecimal(this.cantidad));
        } else {
            this.totalLinea = BigDecimal.ZERO;
        }
    }
}
