package ar.com.acme.impldefault;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.security.SecurityService;
import ar.com.acme.framework.core.token.IToken;
import ar.com.acme.framework.core.token.ITokenAuthentication;
import ar.com.acme.framework.core.token.ITokenPayload;

public class DefaultTokenAuthentication implements ITokenAuthentication<String, ITokenPayload, IToken<String, ITokenPayload>> {
    private final String name;
    private final IToken<String, ITokenPayload> principal;
    private final String details;
    private String credential;
    private boolean authenticated;

    public DefaultTokenAuthentication(IToken<String, ITokenPayload> token, String username, String password) {
        this.name = username;
        this.credential = password;
        this.principal = token;
        this.details = null;
        this.authenticated = false;
    }

    public DefaultTokenAuthentication(IToken<String, ITokenPayload> token, String details) {
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
        // aquí si viene true => debe revalidar la autenticacion
        // antes de proceder a marcarlo como 'realmente' autenticado
        if (isAuthenticated) {
            if (getPrincipal() == null) {
                throw new AuthException(Constantes.MSJ_SES_ERR_INVALIDTOKEN);
            }

            if (getCredentials() != null
                && !SecurityService.passwordsMatch(getCredentials().toString(), principal.getPayload().getCredential())) {
                throw new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL);
            }
        }
        
        this.authenticated = isAuthenticated;
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
