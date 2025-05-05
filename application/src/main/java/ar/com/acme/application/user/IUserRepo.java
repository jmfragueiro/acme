package ar.com.acme.application.user;

import org.springframework.stereotype.Repository;

import ar.com.acme.base.templates.repository.IRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends IRepository<User, UUID> {
    Optional<User> findByToken(UUID token);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String name);
}
