package ar.com.acme.bootstrap.framework.token;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import ar.com.acme.base.utils.principal.IEntityPrincipal;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import lombok.Getter;

@Getter
public class TokenAuthentication implements Authentication, CredentialsContainer {
    private final IEntityPrincipal principal;
    private final String details;
    private final Collection<? extends GrantedAuthority> authorities;
    private String credentials;
    private boolean authenticated;

    public TokenAuthentication(IEntityPrincipal principal, String password, Collection<? extends GrantedAuthority> authorities) {
        this.credentials = password;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
        this.authorities = authorities != null ? authorities : Collections.emptyList();
    }

    public TokenAuthentication(IEntityPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        this.credentials = null;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
        this.authorities = authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public final String getName() {
        return (this.isAuthenticated() && this.principal != null) ? this.principal.toString() : BootstrapConstants.SYS_CAD_UNSESION;
    }

    @Override
    public final void eraseCredentials() {
        this.credentials = null;
    }

    @Override
    public final void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException, AuthException {
        this.authenticated = isAuthenticated;

        // toda vez autentiado se borra el password por seguridad
        if (isAuthenticated) {
            eraseCredentials();
        }
    }

    @Override
    public final boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
