package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Categorias;

/**
 * Repositorio de acceso a datos para la entidad {@link Categorias}.
 * <p>
 * Extiende de {@link JpaRepository}, lo que provee operaciones CRUD básicas
 * y permite definir consultas personalizadas mediante nombres de métodos.
 * </p>
 * 
 * @author rafalopezzz
 */
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

    /**
     * Busca categorías por su nombre.
     * 
     * @param nombre Nombre de la categoría a buscar.
     * @return Lista de categorías que coinciden con el nombre proporcionado.
     */
    List<Categorias> findByNombre(String nombre);

    /**
     * Busca categorías por su descripción.
     * 
     * @param descripcion Descripción de la categoría a buscar.
     * @return Lista de categorías que coinciden con la descripción proporcionada.
     */
    List<Categorias> findByDescripcionContaining(String descripcion);

}
