package com.rlp.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlp.ecommerce.model.Usuario;

/**
 * Repositorio para la entidad {@link Usuario}.
 *
 * <p>
 * Proporciona métodos CRUD básicos heredados de {@link JpaRepository}, así como
 * consultas personalizadas para buscar usuarios por email, rol o tipo de
 * usuario.</p>
 *
 * @author rafalopezzz
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario a buscar.
     * @return {@link Optional} con el {@link Usuario} si existe, o vacío si no se encuentra.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el correo electrónico proporcionado.
     *
     * @param email Correo electrónico a comprobar.
     * @return {@code true} si ya existe un usuario con ese email, {@code false} en caso contrario.
     */
    boolean existsByEmail(String email);

    /**
     * Obtiene una lista de usuarios con el rol especificado.
     *
     * @param rol Rol de los usuarios a buscar (por ejemplo, USER, ADMIN, SUPERADMIN).
     * @return Lista de usuarios que tienen el rol indicado.
     */
    List<Usuario> findByRol(Usuario.RolUsuario rol);


    /**
     * Obtiene una lista de usuarios según su tipo de usuario.
     *
     * @param tipoUsuario Tipo de usuario a buscar (CLIENTE o PROVEEDOR).
     * @return Lista de usuarios que coinciden con el tipo de usuario dado.
     */
    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);
}
