package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.user.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalService implements IPrincipalService<IPrincipal> {
    private final IUserService userService;
    private final IPasswordService passwordService;

    @Override
    public Optional<IPrincipal> findByName(String name) {
        var user = userService.findByName(name);

        if (user.isPresent()) {
            return Optional.of(Principal.from(user.get(), passwordService));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<IPrincipal> findByToken(UUID token) {
        var user = userService.findByToken(token);

        if (user.isPresent()) {
            return Optional.of(Principal.from(user.get(), passwordService));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<String> getAuthorities(IPrincipal principal) {
        // retornar los roles asociados al principal
        return Collections.singleton("ROLE_ADMIN");
    }

    @Override
    public void updatePrincipal(IPrincipal principal) {
        userService.persist(((Principal)principal).getUser());
    }
}
