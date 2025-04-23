package ar.com.acme.application.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.adapter.token.IEntityToken;
import ar.com.acme.adapter.token.IEntityTokenAuthority;
import ar.com.acme.application.common.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "user")
@SequenceGenerator(name = "id_generator", sequenceName = "sg_user_seq", allocationSize = 1)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = User.class)
@Getter
@Setter
@AllArgsConstructor
public class User extends ar.com.acme.adapter.entity.Entity implements IEntityToken {
    public static final String FIELD_USR_NAME = "Nombre";
    public static final String FIELD_USR_EMAIL = "Email";
    public static final String FIELD_USR_PASSWORD = "Password";
    public static final String FIELD_USR_ENABLED = "Habilitado";
    public static final String ERR_NOT_ENABLED = "EL USUARIO NO SE ENCUENTRA HABILITADO";

    @Column(name = "name", unique = true)
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_USR_NAME)
    @Size(min = 4, max = 16, message = AdapterConstants.MSJ_REP_ERR_FIELD_LONG_NOK + FIELD_USR_NAME)
    private String name;

    @Column(name = "email", unique = true)
    @Pattern(regexp = AppConstants.REGEXP_EMAIL, message = AdapterConstants.MSJ_REP_ERR_FIELD_NOK + FIELD_USR_EMAIL)
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_USR_EMAIL)
    private String email;

    @Column(name = "password")
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_USR_PASSWORD)
    private String password;

    @Column(name = "enabled")
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_USR_ENABLED)
    private Boolean enabled;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "token")
    private String token;

    @Override
    public Collection<? extends IEntityTokenAuthority> getAuthorities() {
        return new ArrayList<>(); // todo: llenar con permisos
    }

    @Override
    public String getCredential() {
        return password;
    }

    @Override
    public void verifyCanOperate() {
        if (!getEnabled()) {
            throw new UserException(ERR_NOT_ENABLED, getName());
        }
    }
}
