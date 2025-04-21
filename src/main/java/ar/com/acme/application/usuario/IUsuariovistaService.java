package ar.com.acme.sistema.seguridad.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.com.acme.ports.service.IServicio;

import java.util.Optional;

public interface IUsuariovistaService extends IServicio<Usuariovista, Long>{
    Page<Usuariovista> findUsuarios(String filtro, Pageable pageable);

    Usuariovista findByUsername(String username);

    Optional<String> getUsernameByUsuarioId(Long id);
}
