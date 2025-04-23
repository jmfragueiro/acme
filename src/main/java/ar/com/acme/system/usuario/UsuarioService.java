package ar.com.acme.system.usuario;

import ar.com.acme.bootstrap.common.Constantes;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    private final IUsuarioRepo usuarioRepo;

    @Override
    public Optional<Usuario> findByName(String name) {
        return usuarioRepo.findByUsername(name);
    }

    @Override
    public Optional<Usuario> findByUsername(String usename) {
        return usuarioRepo.findByUsername(usename);
    }

    @Override
    public Optional<Usuario> findByUsernameOrEmail(String parametro) {
        return usuarioRepo.findByUsernameOrEmail(parametro);
    }

    @Override
    public void registrarLogin(String username) {
        Usuario usuario = usuarioRepo.findByUsername(username.toLowerCase()).orElseThrow(() -> new RuntimeException(Constantes.MSJ_REP_ERR_NOITEM));
        usuario.setLastLogin(LocalDateTime.now());
        usuarioRepo.save(usuario);
    }

    @Override
    public Optional<Usuario> findByToken(String token) {
        return usuarioRepo.findByToken(token);
    }
}
