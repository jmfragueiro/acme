package ar.com.acme.application.user;

import org.springframework.stereotype.Repository;

import ar.com.acme.adapter.repository.IRepository;

import java.util.Optional;

@Repository
public interface IUserRepo extends IRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String name);

    Optional<User> findByToken(String token);
}
