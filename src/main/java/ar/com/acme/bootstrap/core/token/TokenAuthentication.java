package ar.com.acme.bootstrap.core.token;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import ar.com.acme.bootstrap.common.Constantes;
import ar.com.acme.bootstrap.core.exception.AuthException;

public class TokenAuthentication implements Authentication, CredentialsContainer {
    private final ITokenPrincipal principal;
    private final String details;
    private String credential;
    private boolean authenticated;

    public TokenAuthentication(ITokenPrincipal principal, String password) {
        this.credential = password;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
    }

    public TokenAuthentication(ITokenPrincipal principal) {
        this.credential = null;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (principal != null) ? principal.getAuthorities() : Collections.emptyList();
    }

    @Override
    public final Object getCredentials() {
        return credential;
    }

    @Override
    public final Object getDetails() {
        return details;
    }

    @Override
    public final Object getPrincipal() {
        return principal;
    }

    @Override
    public final String getName() {
        return (this.isAuthenticated() && this.principal != null) ? this.principal.toString() : Constantes.SYS_CAD_UNSESION;
    }

    @Override
    public final void eraseCredentials() {
        this.credential = null;
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
