package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface IPrincipalService<U extends IPrincipal> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(UUID token);

    Collection<String> getAuthorities(IPrincipal principal);

    void updatePrincipal(IPrincipal principal);
}
