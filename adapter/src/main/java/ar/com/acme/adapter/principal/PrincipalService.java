package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import ar.com.acme.application.password.IPasswordService;
import ar.com.acme.application.user.IUserService;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrincipalService implements IPrincipalService<Principal> {
    private final IUserService userService;
    private final IPasswordService passwordService;

    @Override
    public Optional<Principal> findByName(String name) {
        var user = userService.findByName(name);

        if (user.isPresent()) {
            return Optional.of(Principal.from(user.get(), passwordService));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Principal> findByToken(UUID token) {
        var user = userService.findByToken(token);

        if (user.isPresent()) {
            return Optional.of(Principal.from(user.get(), passwordService));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<String> getAuthorities(IPrincipal principal) {
        // para retornar roles y permisos
        return Collections.emptyList();
    }

    @Override
    public void updatePrincipal(IPrincipal principal) {
        userService.persist(((Principal)principal).getUser());
    }
}
