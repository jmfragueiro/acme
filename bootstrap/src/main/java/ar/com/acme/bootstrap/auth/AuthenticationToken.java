package ar.com.acme.bootstrap.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ar.com.acme.adapter.principal.IPrincipal;
import ar.com.acme.commons.Constants;
import ar.com.acme.bootstrap.exception.AuthException;
import lombok.Getter;

@Getter
public class AuthenticationToken implements Authentication, CredentialsContainer {
    private final IPrincipal principal;
    private final String details;
    private final Collection<? extends GrantedAuthority> authorities;
    private String credentials;
    private boolean authenticated;

    public AuthenticationToken(IPrincipal principal, String password, Collection<String> authorities) {
        this.credentials = password;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
        this.authorities = this.generateAuhtorities(authorities);
    }

    public AuthenticationToken(IPrincipal principal, Collection<String> authorities) {
        this.credentials = null;
        this.principal = principal;
        this.details = null;
        this.authenticated = false;
        this.authorities = this.generateAuhtorities(authorities);
    }

    @Override
    public final String getName() {
        return (this.isAuthenticated() && this.principal != null) ? this.principal.toString() : Constants.SYS_CAD_UNSESION;
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

    private Collection<GrantedAuthority> generateAuhtorities(Collection<String> authStrings) {
        return authStrings != null
                ? authStrings.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
