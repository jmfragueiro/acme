package ar.com.acme.adapter.token;

import java.util.Collection;
import java.util.Optional;

public interface IEntityPrincipalService<U extends IEntityPrincipal> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(String token);

    Collection<IEntityPrincipalAuthority> getAuthorities(IEntityPrincipal principal);

    void updatePrincipal(IEntityPrincipal principal);
}
