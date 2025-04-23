package ar.com.acme.application.usuario;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ar.com.acme.adapter.common.Constantes;
import ar.com.acme.adapter.entity.IEntityToken;
import ar.com.acme.adapter.token.ITokenAuthority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "id_generator", sequenceName = "sg_user_seq", allocationSize = 1)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Usuario.class)
@Getter
@Setter
@AllArgsConstructor
public class Usuario extends ar.com.acme.adapter.entity.Entity implements IEntityToken {
    public static final String F_USR_USUARIO = "Usuario";
    public static final String F_USR_PASSWORD = "Password";
    public static final String F_USR_NOMBRE = "Nombre";
    public static final String F_USR_EMAIL = "Email";

    @Column(name = "username", unique = true)
    @NotNull(message = Constantes.MSJ_REP_ERR_FIELD_EMPTY + F_USR_USUARIO)
    @Size(min = 4, max = 255, message = Constantes.MSJ_REP_ERR_FIELD_LONG_NOK + F_USR_USUARIO)
    private String username;

    @Column(name = "email", unique = true)
    @Size(min = 4, max = 255, message = Constantes.MSJ_REP_ERR_FIELD_LONG_NOK + F_USR_EMAIL)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = Constantes.MSJ_REP_ERR_FIELD_NOK + F_USR_EMAIL)
    @NotNull(message = Constantes.MSJ_REP_ERR_FIELD_EMPTY + F_USR_EMAIL)
    private String email;

    private Boolean enabled;

    private LocalDateTime lastLogin;

    private String token;

    @Transient
    private Collection<? extends ITokenAuthority> auths;

    @Override
    public Collection<? extends ITokenAuthority> getAuthorities() {
        if (auths == null) {
            auths = new ArrayList<>(); // todo: llenar con permisos
        }

        return auths;
    }

    @Override
    public String getCredential() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCredential'");
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }

    @Override
    public String getName() {
        return username;
    }
}
