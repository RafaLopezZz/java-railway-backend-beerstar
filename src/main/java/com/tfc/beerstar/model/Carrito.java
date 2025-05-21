package com.tfc.beerstar.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "carrito", schema = "beerstar_schema")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion = Instant.now();

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<DetalleCarrito> detalleList = new ArrayList<>();

    @Column(name = "subtotal")
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "impuestos")
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(name = "gastos_envio")
    private BigDecimal gastosEnvio = BigDecimal.ZERO;

    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;

     @Column(name = "finalizado")
    private Boolean finalizado = false;
    
    public boolean isFinalizado() {
        return finalizado;
    }
    
    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void recalcularTotales() {
        // Asegurarse que cada detalle tenga su totalLinea calculado
        detalleList.forEach(DetalleCarrito::calcularTotalLinea);

        // Calcular subtotal
        this.subtotal = detalleList.stream()
                .map(DetalleCarrito::getTotalLinea)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Cálculo de impuestos (ejemplo con IVA del 21%)
        this.impuestos = this.subtotal.multiply(new BigDecimal("0.21"));

        // Calcular gastos de envío (ejemplo: gratis si compra > 50€)
        this.gastosEnvio = this.subtotal.compareTo(new BigDecimal("50")) > 0
                ? BigDecimal.ZERO : new BigDecimal("4.99");

        // El total incluye subtotal + impuestos + gastos de envío
        this.total = this.subtotal.add(this.impuestos).add(this.gastosEnvio);
    }

}
