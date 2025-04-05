package com.tfc.beerstar.repository;

import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticulosRepository extends JpaRepository<Articulos, Long> {
    // TODO Agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar artículos por nombre o categoría
    List<Articulos> findByNombreContaining(String nombre);
    List<Articulos> findByCategoria(Categorias categoria);

}
