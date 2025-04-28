package ar.com.acme.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ar.com.acme.base.entity.IEntity;
import ar.com.acme.base.exception.ItemNotFoundException;
import ar.com.acme.base.service.IService;
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
public abstract class Controller<U extends IEntity<TKI>, TKI extends Serializable, X, W> implements IController<U, TKI, X, W> {
    private final IService<U, TKI> service;

    protected Controller(IService<U, TKI> service) {
        this.service = service;
    }

    protected abstract W toWebOutModel(U source);

    protected abstract U fromWebInModel(X source);

    @Override
    public IService<U, TKI> getService() {
        return service;
    }

    @GetMapping(path = "/{key}")
    public ControllerResponse<W> view(@PathVariable("key") TKI key) {
        return ControllerResponse.of(
                ResponseEntity.of(
                    Optional.of(
                        toWebOutModel(service.findById(key)
                                             .orElseThrow(() -> new ItemNotFoundException(key.toString()))))));
    }

    @GetMapping
    public ControllerResponse<Collection<W>> list() {
        var lista = getService().findAllAlive();

        return ControllerResponse.of(
                !lista.isEmpty()
                    ? ResponseEntity.of(Optional.of(lista.stream().map(this::toWebOutModel).toList()))
                    : ResponseEntity.noContent().build());
    }

    @PostMapping(consumes = "application/json")
    public ControllerResponse<W> add(@Valid @RequestBody X object) throws IOException {
        U added = getService().persist(fromWebInModel(object));
        URI location = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(added.getId()).toUri();

        return ControllerResponse.of(ResponseEntity.created(location).body(toWebOutModel(added)));
    }

    @PutMapping(consumes = "application/json")
    public ControllerResponse<W> update(@Valid @RequestBody X object) throws IOException {
        U updated = getService().persist(fromWebInModel(object));

        return ControllerResponse.of(ResponseEntity.accepted().body(toWebOutModel(updated)));
    }

    @DeleteMapping(path = "/{key}")
    public ControllerResponse<Object> delete(@PathVariable("key") TKI key) throws IOException {
        getService().findById(key).ifPresent(service::delete);

        return ControllerResponse.of(ResponseEntity.ok().build());
    }
}
