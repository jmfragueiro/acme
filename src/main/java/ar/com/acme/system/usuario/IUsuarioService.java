package ar.com.acme.system.usuario;

import ar.com.acme.bootstrap.core.token.ITokenPrincipalService;

import java.util.Optional;

public interface IUsuarioService extends ITokenPrincipalService<Usuario> {
    Optional<Usuario> findByUsername(String usename);

    Optional<Usuario> findByUsernameOrEmail(String parametro);

    void registrarLogin(String username);
}
