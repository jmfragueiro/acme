package ar.com.acme.base.templates.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import ar.com.acme.base.exception.ItemNotFoundException;
import ar.com.acme.base.templates.entity.IEntity;
import ar.com.acme.base.templates.service.IService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpRequest;
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

    protected URI getLocation(HttpRequest req, TKI id) {
        try {
            return new URI(req.uri().toString().concat("/").concat(id.toString()));
        } catch (Exception e) {
            throw new ControllerException(e.getLocalizedMessage());
        }
    }

    @Override
    public IService<U, TKI> getService() {
        return service;
    }

    @GetMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.OK)
    public ControllerResponse<W> view(@PathVariable("key") TKI key) {
        return ControllerResponse.of(
                ResponseEntity.of(
                    Optional.of(
                        toWebOutModel(service.findById(key)
                                             .orElseThrow(() -> new ItemNotFoundException(key.toString()))))));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ControllerResponse<Collection<W>> list() {
        var lista = getService().findAllAlive();

        return ControllerResponse.of(
                !lista.isEmpty()
                    ? ResponseEntity.of(Optional.of(lista.stream().map(this::toWebOutModel).toList()))
                    : ResponseEntity.noContent().build());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ControllerResponse<W> add(@Valid @RequestBody X object, HttpRequest req) throws IOException {
        U added = getService().persist(fromWebInModel(object));

        return ControllerResponse.of(ResponseEntity.created(getLocation(req, added.getId())).body(toWebOutModel(added)));
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ControllerResponse<W> update(@Valid @RequestBody X object) throws IOException {
        U updated = getService().persist(fromWebInModel(object));

        return ControllerResponse.of(ResponseEntity.accepted().body(toWebOutModel(updated)));
    }

    @DeleteMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ControllerResponse<Object> delete(@PathVariable("key") TKI key) throws IOException {
        getService().findById(key).ifPresent(service::delete);

        return ControllerResponse.of(ResponseEntity.ok().build());
    }
}
