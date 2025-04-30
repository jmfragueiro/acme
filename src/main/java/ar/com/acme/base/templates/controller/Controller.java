package ar.com.acme.base.templates.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import ar.com.acme.base.exception.ItemNotFoundException;
import ar.com.acme.base.templates.entity.IEntity;
import ar.com.acme.base.templates.service.IService;
import jakarta.validation.Valid;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

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

    // esto se saca
    protected abstract W toWebOutModel(U source);

    protected abstract U fromWebInModel(X source);

    @Override
    public IService<U, TKI> getService() {
        return service;
    }

    @GetMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.OK)
    public W view(@PathVariable("key") TKI key) {
        return toWebOutModel(service.findById(key).orElseThrow(() -> new ItemNotFoundException(key.toString())));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<W> list() {
        var lista = getService().findAllAlive();

        return lista.stream().map(this::toWebOutModel).toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public W add(@Valid @RequestBody X object) throws IOException {
        U added = getService().persist(fromWebInModel(object));

        return toWebOutModel(added);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public W update(@Valid @RequestBody X object) throws IOException {
        U updated = getService().persist(fromWebInModel(object));

        return toWebOutModel(updated);
    }

    @DeleteMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("key") TKI key) throws IOException {
        getService().findById(key).ifPresent(service::delete);
    }
}
