package com.tfc.beerstar.repository;

import com.tfc.beerstar.model.Categorias;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    //  TODO Agregar métodos personalizados si es necesario
    //  Por ejemplo, para buscar categorías por nombre o descripción
    List<Categorias> findByNombreContaining(String nombre);
    List<Categorias> findByDescripcionContaining(String descripcion);

}
