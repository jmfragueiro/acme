package ar.com.acme.application.user;

import java.util.Optional;
import java.util.UUID;

import ar.com.acme.application.templates.service.IService;

public interface IUserService extends IService<User, UUID> {
    Optional<User> findByName(String name);

    Optional<User> findByToken(UUID token);

    Optional<User> findByEmail(String parametro);

    void registrarLogin(User user);

    Boolean isValidEmail(String email);

    Boolean isValidPassword(String rawPassword);

    String encodePassword(CharSequence rawPassword);
}
