package ar.com.acme.application.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService extends ar.com.acme.adapter.service.Service<User, Long> implements IUserService {
    public UserService(IUserRepo usuarioRepo) {
        super(usuarioRepo);
    }

    @Override
    public Optional<User> findByName(String name) {
        return ((IUserRepo)getRepo()).findByName(name);
    }

    @Override
    public Optional<User> findByEmail(String parametro) {
        return ((IUserRepo)getRepo()).findByEmail(parametro);
    }

    @Override
    public void registrarLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        ((IUserRepo)getRepo()).save(user);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return ((IUserRepo)getRepo()).findByToken(token);
    }
}
