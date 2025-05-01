package com.tfc.beerstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfc.beerstar.model.Lotes;
import com.tfc.beerstar.model.Proveedor;

/**
 * Repositorio de acceso a datos para la entidad {@link Lotes}.
 * <p>
 * Extiende de {@link JpaRepository}, lo que provee operaciones CRUD básicas
 * y permite definir consultas personalizadas mediante nombres de métodos.
 * </p>
 * 
 * @author rafalopezzz
 */
public interface LoteRepository extends JpaRepository<Lotes, Long> {
    
    /**
     * Busca lotes por su proveedor.
     * 
     * @param proveedor Proveedor asociado a los lotes.
     * @return Lista de lotes que pertenecen al proveedor indicado.
     */
    List<Lotes> findByProveedor(Proveedor proveedor);
    
    /**
     * Busca lotes por su ID.
     * 
     * @param idLote ID del lote a buscar.
     * @return Lista de lotes que coinciden con el ID proporcionado.
     */
    List<Lotes> findByIdLote(Long idLote);
    
    /**
     * Busca lotes por su nombre.
     * 
     * @param nombre Nombre del lote a buscar.
     * @return Lista de lotes que coinciden con el nombre proporcionado.
     */
    List<Lotes> findByDescripcionContaining(String descripcion);
    
    /**
     * Busca lotes cuyo precio esté dentro de un rango determinado.
     *
     * @param minPrecio Precio mínimo.
     * @param maxPrecio Precio máximo.
     * @return Lista de lotes cuyo precio esté entre esos dos valores (inclusive).
     */
    List<Lotes> findByPrecioBetween(Double minPrecio, Double maxPrecio);

}
