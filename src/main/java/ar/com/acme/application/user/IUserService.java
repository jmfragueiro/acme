package ar.com.acme.application.user;

import java.util.Optional;
import java.util.UUID;

import ar.com.acme.base.principal.IEntityPrincipalService;
import ar.com.acme.base.service.IService;

public interface IUserService extends IService<User, UUID>, IEntityPrincipalService<User> {
    Optional<User> findByEmail(String parametro);

    void registrarLogin(User user);

    Boolean isValidEmail(String email);

    Boolean isValidPassword(String password);
}
