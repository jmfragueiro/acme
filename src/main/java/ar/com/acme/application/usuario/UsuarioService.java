package ar.com.acme.application.usuario;

import ar.com.acme.adapter.common.Constantes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsuarioService extends ar.com.acme.adapter.service.Service<Usuario, Long> implements IUsuarioService {
    public UsuarioService(IUsuarioRepo usuarioRepo) {
        super(usuarioRepo);
    }

    @Override
    public Optional<Usuario> findByName(String name) {
        return ((IUsuarioRepo)getRepo()).findByUsername(name);
    }

    @Override
    public Optional<Usuario> findByUsername(String usename) {
        return ((IUsuarioRepo)getRepo()).findByUsername(usename);
    }

    @Override
    public Optional<Usuario> findByUsernameOrEmail(String parametro) {
        return ((IUsuarioRepo)getRepo()).findByUsernameOrEmail(parametro);
    }

    @Override
    public void registrarLogin(String username) {
        Usuario usuario = ((IUsuarioRepo)getRepo()).findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException(Constantes.MSJ_REP_ERR_NOITEM));
        usuario.setLastLogin(LocalDateTime.now());
        ((IUsuarioRepo)getRepo()).save(usuario);
    }

    @Override
    public Optional<Usuario> findByToken(String token) {
        return ((IUsuarioRepo)getRepo()).findByToken(token);
    }
}
