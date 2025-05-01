package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Articulos;
import com.tfc.beerstar.model.Categorias;

/**
 * Repositorio para la entidad {@link Articulos}.
 *
 * <p>Esta interfaz proporciona los métodos necesarios para acceder y manipular los datos
 * relacionados con los artículos en la base de datos. Extiende de {@link JpaRepository},
 * lo que le otorga funcionalidades CRUD básicas como guardar, eliminar, actualizar y buscar artículos.</p>
 *
 * <p>Además, contiene métodos personalizados para realizar búsquedas específicas de artículos
 * basados en el nombre o la categoría a la que pertenecen.</p>
 * 
 * @author rafalopezzz
 */
public interface ArticulosRepository extends JpaRepository<Articulos, Long> {

    /**
     * Busca una lista de artículos cuyo nombre contenga la cadena proporcionada.
     * 
     * <p>Este método realiza una búsqueda insensible a mayúsculas y minúsculas,
     * devolviendo artículos cuyo nombre contenga la cadena dada.</p>
     *
     * @param nombre La cadena que se busca en el nombre del artículo.
     * @return Lista de artículos cuyo nombre contiene la cadena especificada.
     */
    List<Articulos> findByNombreContaining(String nombre);
    
    /**
     * Busca una lista de artículos que pertenecen a la categoría proporcionada.
     * 
     * <p>Este método realiza una búsqueda para obtener todos los artículos asociados
     * a la categoría especificada.</p>
     *
     * @param categoria La categoría a la que pertenecen los artículos.
     * @return Lista de artículos que pertenecen a la categoría dada.
     */
    List<Articulos> findByCategoria(Categorias categoria);
}
