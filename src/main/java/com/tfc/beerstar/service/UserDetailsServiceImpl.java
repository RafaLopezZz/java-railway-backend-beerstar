package com.tfc.beerstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfc.beerstar.model.Usuario;
import com.tfc.beerstar.repository.UsuarioRepository;
import com.tfc.beerstar.security.UserDetailsImpl;

/**
 * Implementación de {@link UserDetailsService} de Spring Security.
 *
 * <p>Esta clase carga los detalles del usuario (credenciales y autoridades) a partir
 * de la entidad {@link Usuario} almacenada en la base de datos mediante
 * {@link UsuarioRepository}.</p>
 *
 * <p>Se utiliza en el proceso de autenticación para validar el email y obtener
 * la contraseña cifrada y roles asociados.</p>
 *
 * @see UserDetailsService
 * @see UsernameNotFoundException
 * @see UserDetailsImpl
 * @author rafalopezzz
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    /**
     * Repositorio para acceder a la tabla de usuarios.
     */
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles de un usuario por su email.
     *
     * <p>Este método se invoca durante la autenticación. Busca en la base de datos
     * la entidad {@link Usuario} cuyo email coincide con el parámetro. Si no existe,
     * lanza {@link UsernameNotFoundException}.</p>
     *
     * <p>La operación está dentro de una transacción de solo lectura.
     * Se construye un objeto {@link UserDetailsImpl} que contiene:
     * <ul>
     *   <li>Email (username).</li>
     *   <li>Contraseña cifrada.</li>
     *   <li>Lista de autoridades (roles y tipo de usuario).</li>
     * </ul>
     * </p>
     *
     * @param email Email del usuario que intenta autenticarse
     * @return Instancia de {@link UserDetails} con los datos del usuario
     * @throws UsernameNotFoundException Si no se encuentra un usuario con el email dado
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Convierte la entidad Usuario en UserDetailsImpl para Spring Security
        return UserDetailsImpl.build(usuario);
    }
}