package ar.com.acme.application.user;

import java.util.Optional;
import java.util.UUID;

import ar.com.acme.adapter.service.IService;
import ar.com.acme.adapter.token.IEntityPrincipalService;

public interface IUserService extends IService<User, UUID>, IEntityPrincipalService<User> {
    Optional<User> findByEmail(String parametro);

    void registrarLogin(User user);
}
