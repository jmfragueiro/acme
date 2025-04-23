package ar.com.acme.adapter.token;

import java.util.Optional;

public interface IEntityTokenService<U extends IEntityToken> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(String token);
}
