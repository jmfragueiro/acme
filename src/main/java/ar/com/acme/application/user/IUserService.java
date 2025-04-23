package ar.com.acme.application.user;

import java.util.Optional;

import ar.com.acme.adapter.service.IService;
import ar.com.acme.adapter.token.IEntityTokenService;

public interface IUserService extends IService<User, Long>, IEntityTokenService<User> {
    Optional<User> findByName(String usename);

    Optional<User> findByEmail(String parametro);

    void registrarLogin(User user);
}
