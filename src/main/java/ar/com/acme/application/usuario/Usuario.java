package ar.com.acme.sistema.seguridad.usuario;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.token.ITokenAuthority;
import ar.com.acme.ports.entity.Entidad;
import ar.com.acme.impldefault.DefaultTokenAuthority;
import ar.com.acme.ports.security.ITokenUser;
import ar.com.acme.sistema.seguridad.grupo.Grupo;
import ar.com.acme.sistema.seguridad.grupopermiso.GrupoPermiso;
import ar.com.acme.sistema.seguridad.usuariogrupo.UsuarioGrupo;
import ar.com.acme.sistema.persona.persona.Persona;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "sg_user")
@SequenceGenerator(name = "id_generator", sequenceName = "sg_user_seq", allocationSize = 1)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Usuario.class)
@Getter
@Setter
@AllArgsConstructor
public class Usuario extends Entidad implements ITokenUser {
    public static final String F_USR_USUARIO = "Usuario";
    public static final String F_USR_PASSWORD = "Password";
    public static final String F_USR_NOMBRE = "Nombre";
    public static final String F_USR_EMAIL = "Email";

    @OneToOne()
    @JoinColumn(name = "pe_persona_id", nullable = true)
    private Persona persona;

    @Column(name = "username", unique = true)
    @NotNull(message = Constantes.MSJ_ERR_DB_FIELD_EMPTY + F_USR_USUARIO)
    @Size(min = 4, max = 255, message = Constantes.MSJ_ERR_DB_FIELD_LONGNOK + F_USR_USUARIO)
    private String username;

    @Column(name = "email", unique = true)
    @Size(min = 4, max = 255, message = Constantes.MSJ_ERR_DB_FIELD_LONGNOK + F_USR_EMAIL)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = Constantes.MSJ_ERR_DB_FIELD_NOK + F_USR_EMAIL)
    @NotNull(message = Constantes.MSJ_ERR_DB_FIELD_EMPTY + F_USR_EMAIL)
    private String email;

    private LocalDateTime emailcheckAt;

    private String emailchecktoken;

    private String password;

    private String foto;

    private Boolean enabled;

    private LocalDateTime lastLogin;

    private Boolean locked;

    private Boolean expired;

    private LocalDate expiresAt;

    private String tokenresetpassword;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("grupo")
    @SQLRestriction("fechabaja is null")
    private Set<UsuarioGrupo> grupos = new HashSet<>();

    @Transient
    private Collection<? extends ITokenAuthority> auths;

    public void onLogin() {
        setLastLogin(LocalDateTime.now());
    }

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public String getCredential() {
        return password;
    }

    @Override
    public Collection<? extends ITokenAuthority> reinitAuthorities() {
        this.auths = getGrupos().stream()
                                .map(UsuarioGrupo::getGrupo)
                                .map(Grupo::getPermisos)
                                .flatMap(Collection::stream)
                                .map(GrupoPermiso::getPermiso)
                                .map(p -> new DefaultTokenAuthority(p.getPermiso()))
                                .collect(Collectors.toList());

        return this.auths;
    }

    @Override
    public Collection<? extends ITokenAuthority> getAuthorities() {
        return (auths == null) ? this.reinitAuthorities() : this.auths;
    }

    @Override
    public boolean isNonExpired() {
        return (expired == null || !expired);
    }

    @Override
    public boolean isNonLocked() {
        return (locked == null || !locked);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
