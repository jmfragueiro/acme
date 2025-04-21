package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.token.ITokenAuthority;
import ar.com.acme.framework.core.token.ITokenPayload;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public record RDefaultUser(
        Long id,
        String username,
        String password,
        Boolean locked,
        Boolean enabled,
        Boolean expired) implements ITokenPayload {
    private static Collection<ITokenAuthority> auths;

    public RDefaultUser {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(locked);
        Objects.requireNonNull(enabled);
        Objects.requireNonNull(expired);

        auths = new HashSet<>();
        auths.add(new DefaultTokenAuthority(Constantes.SYS_APP_ROLE_ADMIN));
        auths.add(new DefaultTokenAuthority(Constantes.SYS_APP_ROLE_BASIC));
        auths.add(new DefaultTokenAuthority(Constantes.SYS_APP_ROLE_USER));
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getCredential() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isNonLocked() {
        return !locked;
    }

    @Override
    public boolean isNonExpired() {
        return !expired;
    }

    @Override
    public Collection<? extends ITokenAuthority> reinitAuthorities() {
        return auths;
    }

    @Override
    public Collection<? extends ITokenAuthority> getAuthorities() {
        return auths;
    }
}
