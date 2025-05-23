package com.tfc.beerstar.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfc.beerstar.model.Usuario;

/**
 * Implementación de {@link org.springframework.security.core.userdetails.UserDetails}
 * que adapta la entidad {@link com.tfc.beerstar.model.Usuario} al modelo de Spring Security.
 *
 * <p>Incluye rol y tipo de usuario como autoridades para permitir control de acceso
 * basado en roles y categorías de usuario.</p>
 *
 * @author rafalopezzz
 */
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    /** Identificador único del usuario. */
    private final Long idUsuario;

    /** Email utilizado como nombre de usuario para autenticación. */
    private final String email;

    /** Tipo de usuario (CLIENTE o PROVEEDOR). */
    private final String tipoUsuario;

    /** Contraseña cifrada del usuario. No se serializa en respuestas JSON. */
    @JsonIgnore
    private final String password;

    /** Conjunto de autoridades (roles y tipo de usuario) asignadas. */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Construye la instancia con todos los atributos.
     *
     * @param idUsuario     Identificador del usuario
     * @param email         Email del usuario (username)
     * @param password      Contraseña cifrada
     * @param tipoUsuario   Tipo de usuario (CLIENTE o PROVEEDOR)
     * @param authorities   Autoridades (roles y tipos)
     */
    public UserDetailsImpl(Long idUsuario,
                           String email,
                           String password,
                           String tipoUsuario,
                           Collection<? extends GrantedAuthority> authorities) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.authorities = authorities;
    }

    /**
     * Crea una instancia de {@code UserDetailsImpl} a partir de una entidad {@link Usuario}.
     *
     * <p>Se añaden dos tipos de autoridad:
     * <ul>
     *   <li>"ROLE_{rol}" si el usuario tiene rol asignado.</li>
     *   <li>"TYPE_{tipoUsuario}" para discriminar cliente o proveedor.</li>
     * </ul>
     *
     * @param usuario Entidad de dominio a adaptar
     * @return Objeto de detalles de usuario para Spring Security
     */
    public static UserDetailsImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Añadir rol principal, prefijado con "ROLE_"
        if (usuario.getRol() != null && !usuario.getRol().isBlank()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
        }
        // Añadir tipo de usuario como autoridad adicional, prefijado con "TYPE_"
        if (usuario.getTipoUsuario() != null && !usuario.getTipoUsuario().isBlank()) {
            authorities.add(new SimpleGrantedAuthority("TYPE_" + usuario.getTipoUsuario()));
        }

        return new UserDetailsImpl(
                usuario.getIdUsuario(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getTipoUsuario(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return Identificador único del usuario
     */
    public Long getId() {
        return idUsuario;
    }

    /**
     * @return Correo electrónico del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Tipo de usuario (CLIENTE o PROVEEDOR)
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Siempre activo; implementar lógica si se requiere expiración
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Siempre desbloqueado; implementar lógica si se requiere bloqueo
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Siempre válidas; implementar lógica si se requiere expiración de credenciales
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Siempre habilitado; implementar lógica si se requiere deshabilitación
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(idUsuario, that.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }
}
