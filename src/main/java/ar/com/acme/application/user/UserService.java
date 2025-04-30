package ar.com.acme.application.user;

import org.springframework.stereotype.Service;

import ar.com.acme.application.common.AppProperties;
import ar.com.acme.base.utils.passw.IPasswordService;
import ar.com.acme.base.utils.principal.IEntityPrincipal;
import ar.com.acme.base.utils.principal.IEntityPrincipalAuthority;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

@Service
public class UserService extends ar.com.acme.base.templates.service.Service<User, UUID> implements IUserService {
    private final Predicate<String> isValidEmail;

    private final IPasswordService passwordService;

    public UserService(IUserRepo usuarioRepo, AppProperties appProperties, IPasswordService passwordService) {
        super(usuarioRepo);
        this.isValidEmail = email -> { return email.matches(appProperties.getRegexp().get("email")); };
        this.passwordService = passwordService;
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
    public Boolean isValidPassword(String rawPassword) {
        return passwordService.isValidPassword(rawPassword);
    }

    @Override
    public String encodePassword(CharSequence rawPassword) {
        return passwordService.encode(rawPassword);
    }
}
