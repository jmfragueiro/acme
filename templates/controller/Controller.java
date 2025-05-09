package ar.com.acme.application.templates.controller;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.application.templates.entity.IEntity;
import ar.com.acme.application.templates.repository.ItemNotFoundException;
import ar.com.acme.application.templates.service.IService;
import ar.com.acme.commons.Constants;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class Controller<U extends IEntity<TKI>, TKI extends Serializable, WI, WO> implements IController<U, TKI, WI, WO> {
    private final IService<U, TKI> service;

    protected Controller(IService<U, TKI> service) {
        this.service = service;
    }

    @Override
    public IService<U, TKI> getService() {
        return service;
    }

    @GetMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.OK)
    public WO view(@PathVariable("key") TKI key) {
        return toWebOutModel(service.findById(key).orElseThrow(() -> new ItemNotFoundException(key.toString())));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<WO> list() {
        var lista = getService().findAllAlive();

        return lista.stream().map(this::toWebOutModel).toList();
    }

    @PostMapping(consumes = Constants.SYS_CAD_APP_MIMETYPE_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public WO add(@Valid @RequestBody WI object) throws IOException {
        U added = getService().persist(fromWebInModel(object));

        return toWebOutModel(added);
    }

    @PutMapping(consumes = Constants.SYS_CAD_APP_MIMETYPE_JSON)
    @ResponseStatus(HttpStatus.OK)
    public WO update(@Valid @RequestBody WI object) throws IOException {
        U updated = getService().persist(fromWebInModel(object));

        return toWebOutModel(updated);
    }

    @DeleteMapping(path = "/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("key") TKI key) throws IOException {
        getService().findById(key).ifPresent(service::delete);
    }
}
