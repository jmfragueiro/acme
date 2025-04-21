package ar.com.acme.ports.control;

import ar.com.acme.framework.core.exception.ItemNotFoundException;
import ar.com.acme.ports.entity.IEntidad;
import ar.com.acme.ports.service.IServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
@RestController
public abstract class Controlador<U extends IEntidad<TKI>, TKI extends Serializable> implements IControlador<U, TKI> {
    private final IServicio<U, TKI> servicio;

    @PersistenceContext
    private EntityManager entityManager;

    protected Controlador(IServicio<U, TKI> servicio) {
        this.servicio = servicio;
    }

    @Override
    public IServicio<U, TKI> getServicio() {
        return servicio;
    }

    @GetMapping(path = "/{key}")
    public CtrlResponse<U> view(@PathVariable("key") TKI key) {
        return CtrlResponse.of(
                ResponseEntity.of(
                    Optional.of(
                        servicio.findById(key).orElseThrow(() -> new ItemNotFoundException(key.toString())))));
    }

    @GetMapping
    public CtrlResponse<Collection<U>> list() {
        var lista = getServicio().findAllAlive();

        return CtrlResponse.of(
                !lista.isEmpty()
                    ? ResponseEntity.of(Optional.of(lista))
                    : ResponseEntity.noContent().build());
    }

    @PostMapping(consumes = "application/json")
    public CtrlResponse<Object> add(@Valid @RequestBody U object) throws IOException {
        U added = getServicio().persist(object);
        URI location = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(added.getId()).toUri();

        return CtrlResponse.of(ResponseEntity.created(location).body(added));
    }

    @PutMapping(consumes = "application/json")
    public CtrlResponse<Object> update(@Valid @RequestBody U object) throws IOException {
        U updated = getServicio().persist(object);

        return CtrlResponse.of(ResponseEntity.accepted().body(updated));
    }

    @DeleteMapping(path = "/{key}")
    public CtrlResponse<Object> delete(@PathVariable("key") TKI key) throws IOException {
        getServicio().findById(key).ifPresent(servicio::delete);

        return CtrlResponse.of(ResponseEntity.ok().build());
    }
}
