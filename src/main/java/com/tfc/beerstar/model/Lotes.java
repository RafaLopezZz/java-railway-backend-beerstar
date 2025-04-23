package com.tfc.beerstar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "lotes", schema = "beerstar_schema")
@Data
public class Lotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Long idLote;

    @Column(name = "nombre_lote", nullable = false)
    private String nombreLote;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne 
    @Column(name = "id_proveedor")
    private Proveedor proveedor;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "url")
    private String url;

    @Column(name = "fecha_validez")
    private LocalDateTime fechaValidez = LocalDateTime.now();

}
