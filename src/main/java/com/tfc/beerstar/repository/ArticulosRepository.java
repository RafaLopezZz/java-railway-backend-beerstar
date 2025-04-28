package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;

@Repository
public interface ArticulosRepository extends JpaRepository<Articulos, Long> {

    List<Articulos> findByNombreContaining(String nombre);
    List<Articulos> findByCategoria(Categorias categoria);

}
