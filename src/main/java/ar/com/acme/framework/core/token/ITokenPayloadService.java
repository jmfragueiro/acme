package ar.com.acme.framework.core.token;

import java.util.Optional;

public interface ITokenPayloadService<U extends ITokenPayload> {
    Optional<U> findByName(String name);
}
