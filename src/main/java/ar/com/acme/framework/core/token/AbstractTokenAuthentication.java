package ar.gov.posadas.mbe.framework.core.token;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;

import ar.gov.posadas.mbe.framework.core.exception.AuthException;

public abstract class AbstractTokenAuthentication implements ITokenAuthentication<String, ITokenPayload, IToken<String, ITokenPayload>> {
    private final String name;
    private final IToken<String, ITokenPayload> principal;
    private final String details;
    private String credential;
    private boolean authenticated;

    public AbstractTokenAuthentication(IToken<String, ITokenPayload> token, String username, String password) {
        this.name = username;
        this.credential = password;
        this.principal = token;
        this.details = null;
        this.authenticated = false;
    }

    public AbstractTokenAuthentication(IToken<String, ITokenPayload> token, String details) {
        this.name = null;
        this.credential = null;
        this.principal = token;
        this.details = details;
        this.authenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (principal != null) ? principal.getPayload().getAuthorities() : Collections.emptyList();
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
        return (this.isAuthenticated() && this.principal != null
                ? this.principal.getPayload().getName()
                : this.name);
    }

    @Override
    public final void eraseCredentials() {
        this.credential = null;
    }

    @Override
    public final void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException, AuthException {
        // aquí, si viene true, debe validar la autenticacion
        // antes de proceder a marcarlo como autenticado
        if (isAuthenticated) {
            this.validateAuthentication();
        }
        this.authenticated = isAuthenticated;
    }

    @Override
    public final boolean isAuthenticated() {
        return this.authenticated;
    }

    protected abstract void validateAuthentication() throws IllegalArgumentException, AuthException;
}
