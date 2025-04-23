package ar.com.acme.application.usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.acme.adapter.repository.IRepository;

import java.util.Optional;

@Repository
public interface IUsuarioRepo extends IRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByToken(String token);

    @Query("SELECT u.username FROM Usuario u WHERE u.id = ?1 ")
    Optional<String> getUsernameByUsuarioId(Long id);

    @Query("select u from Usuario u where lower(u.username) like ?1 or lower(u.email) like ?1 ")
    Optional<Usuario> findByUsernameOrEmail(String parametro);
}
