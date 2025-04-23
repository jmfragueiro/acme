package ar.com.acme.adapter.token;

import java.util.Optional;

public interface ITokenPrincipalService<U extends ITokenPrincipal> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(String token);
}
