package ar.com.acme.application.user;

import org.springframework.stereotype.Service;

import ar.com.acme.application.common.AppProperties;
import ar.com.acme.base.principal.IEntityPrincipal;
import ar.com.acme.base.principal.IEntityPrincipalAuthority;
import ar.com.acme.base.service.ServiceException;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Service
public class UserService extends ar.com.acme.base.service.Service<User, UUID> implements IUserService {
    private final Predicate<String> isValidEmail;
    private final Predicate<String> isValidPassword;

    public UserService(IUserRepo usuarioRepo, AppProperties appProperties) {
        super(usuarioRepo);
        this.isValidEmail = email -> { return email.matches(appProperties.getRegexp().get("email")); };
        this.isValidPassword = password -> { return password.matches(appProperties.getRegexp().get("password")); };
    }

    @Override
    public User persist(User instancia) throws ServiceException {
        return super.persist(instancia);
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
    public Optional<User> findByToken(UUID token) {
        return ((IUserRepo)getRepo()).findByToken(token);
    }

    @Override
    public Collection<IEntityPrincipalAuthority> getAuthorities(IEntityPrincipal principal) {
        return Collections.emptyList();
    }

    @Override
    public void updatePrincipal(IEntityPrincipal principal) {
        persist((User)principal);
    }

    @Override
    public Boolean isValidEmail(String email) {
        return isValidEmail.test(email);
    }

    @Override
    public Boolean isValidPassword(String password) {
        return isValidPassword.test(password);
    }
}
