package ar.com.acme.application.usuario;

import java.util.Optional;

import ar.com.acme.adapter.service.IService;
import ar.com.acme.adapter.token.ITokenPrincipalService;

public interface IUsuarioService extends IService<Usuario, Long>, ITokenPrincipalService<Usuario> {
    Optional<Usuario> findByUsername(String usename);

    Optional<Usuario> findByUsernameOrEmail(String parametro);

    void registrarLogin(String username);
}
