package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Lotes;
import com.tfc.beerstar.model.Proveedor;

public interface LoteRepository extends JpaRepository<Lotes, Long> {
    // Métodos personalizados si es necesario
    List<Lotes> findByProveedor(Proveedor proveedor);
    List<Lotes> findByIdLote(Long idLote);
    List<Lotes> findByDescripcionContaining(String descripcion);
    List<Lotes> findByPrecioBetween(Double minPrecio, Double maxPrecio);

}
