package ar.com.acme.base.utils.principal;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface IEntityPrincipalService<U extends IEntityPrincipal> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(UUID token);

    Collection<IEntityPrincipalAuthority> getAuthorities(IEntityPrincipal principal);

    void updatePrincipal(IEntityPrincipal principal);
}
