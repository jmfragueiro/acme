package ar.com.acme.system.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.com.acme.adapter.service.IService;

import java.util.Optional;

public interface IUsuariovistaService extends IService<Usuariovista, Long>{
    Page<Usuariovista> findUsuarios(String filtro, Pageable pageable);

    Usuariovista findByUsername(String username);

    Optional<String> getUsernameByUsuarioId(Long id);
}
