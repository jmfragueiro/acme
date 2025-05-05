package ar.com.acme.application.user;

import java.util.Optional;
import java.util.UUID;

import ar.com.acme.application.common.templates.service.IService;
import ar.com.acme.application.principal.IPrincipalService;

public interface IUserService extends IService<User, UUID>, IPrincipalService<User> {
    Optional<User> findByEmail(String parametro);

    void registrarLogin(User user);

    Boolean isValidEmail(String email);

    Boolean isValidPassword(String rawPassword);

    String encodePassword(CharSequence rawPassword);
}
