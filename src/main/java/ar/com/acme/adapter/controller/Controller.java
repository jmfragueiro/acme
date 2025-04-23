package ar.com.acme.adapter.controller;

import ar.com.acme.adapter.entity.IEntity;
import ar.com.acme.adapter.repository.IRepository;
import ar.com.acme.bootstrap.core.exception.ItemNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import jakarta.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementación de interfase IControlador para un sistema con REST-JPA. Esta clase es
 * ademas la base esperada de controladores REST para el sistema en el framework ad-hoc.
 *
 * @param <U>   El tipo de la entidad servida por el servicio
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public abstract class Controller<U extends IEntity<TKI>, TKI extends Serializable, W> implements IController<U, TKI, W> {
    private final IRepository<U, TKI> repo;

    protected Controller(IRepository<U, TKI> repo) {
        this.repo = repo;
    }

    protected abstract W toWebModel(U source);

    protected abstract U toEntityModel(W source);

    @Override
    public IRepository<U, TKI> getRepo() {
        return repo;
    }

    @GetMapping(path = "/{key}")
    public ControllerResponse<W> view(@PathVariable("key") TKI key) {
        return ControllerResponse.of(
                ResponseEntity.of(
                    Optional.of(
                        toWebModel(repo.findById(key).orElseThrow(() -> new ItemNotFoundException(key.toString()))))));
    }

    @GetMapping
    public ControllerResponse<Collection<W>> list() {
        var lista = getRepo().findAllAlive();

        return ControllerResponse.of(
                !lista.isEmpty()
                    ? ResponseEntity.of(Optional.of(lista.stream().map(this::toWebModel).toList()))
                    : ResponseEntity.noContent().build());
    }

    @PostMapping(consumes = "application/json")
    public ControllerResponse<Object> add(@Valid @RequestBody U object) throws IOException {
        U added = getRepo().save(object);
        URI location = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(added.getId()).toUri();

        return ControllerResponse.of(ResponseEntity.created(location).body(toWebModel(added)));
    }

    @PutMapping(consumes = "application/json")
    public ControllerResponse<W> update(@Valid @RequestBody U object) throws IOException {
        U updated = getRepo().save(object);

        return ControllerResponse.of(ResponseEntity.accepted().body(toWebModel(updated)));
    }

    @DeleteMapping(path = "/{key}")
    public ControllerResponse<Object> delete(@PathVariable("key") TKI key) throws IOException {
        getRepo().findById(key).ifPresent(repo::delete);

        return ControllerResponse.of(ResponseEntity.ok().build());
    }
}
