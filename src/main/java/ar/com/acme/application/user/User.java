package ar.com.acme.application.user;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.adapter.token.IEntityToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
public class User extends ar.com.acme.adapter.entity.Entity implements IEntityToken {
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_PASSWORD = "Password";
    public static final String FIELD_ENABLED = "Enabled";
    public static final String ERR_NOT_ENABLED = "EL USUARIO NO SE ENCUENTRA HABILITADO";
    public static final String ERR_BAD_EMAIL = "LA DIRECCION DE EMAIL INGRESADA NO TIENE FORMATO VALIDO";
    public static final String ERR_BAD_PASSWORD = "EL PASSWORD INGRESADO NO TIENE FORMATO VALIDO";

    @Column(name = "name", unique = true)
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_NAME)
    @Size(min = 4, max = 16, message = AdapterConstants.MSJ_REP_ERR_FIELD_LONG_NOK + FIELD_NAME)
    private String name;

    @Column(name = "email", unique = true)
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_EMAIL)
    private String email;

    @Column(name = "password")
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_PASSWORD)
    private String password;

    @Column(name = "enabled")
    @NotNull(message = AdapterConstants.MSJ_REP_ERR_FIELD_EMPTY + FIELD_ENABLED)
    private Boolean enabled;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "token")
    private String token;

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
