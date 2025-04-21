package ar.com.acme.ports.control;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Response;
import ar.com.acme.framework.core.exception.ItemNotFoundException;
import ar.com.acme.ports.entity.IEntidad;
import ar.com.acme.ports.service.IServicio;
import ar.com.acme.ports.service.ServiceException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public abstract class Controlador<U extends IEntidad<TKI>, TKI extends Serializable> implements IControlador<U, TKI> {
    private final IServicio<U, TKI> servicio;

    @PersistenceContext
    private EntityManager entityManager;

    protected Controlador(IServicio<U, TKI> servicio) {
        this.servicio = servicio;
    }

    protected final Map<String, Object> getMessageMap(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
        return errores;
    }

    @Override
    public IServicio<U, TKI> getServicio() {
        return servicio;
    }

    @Override
    public String getNombreEntidadAsociada() {
        return servicio.getNombreEntidadAsociada();
    }

    @GetMapping(path = "/{key}", produces = "application/json")
    public CtrlResponse<U> viewdit(@PathVariable("key") TKI key, HttpServletRequest req) {
        return CtrlResponse.of(
                ResponseEntity.of(
                    Optional.of(
                        servicio.findById(key)
                                .orElseThrow(() -> new ItemNotFoundException(getNombreEntidadAsociada(), key.toString())))));
    }

    @GetMapping(produces = "application/json")
    public CtrlResponse<Collection<U>> list(HttpServletRequest req) {
        var lista = getServicio().findAllAlive();

        return CtrlResponse.of(
                !lista.isEmpty()
                    ? ResponseEntity.of(Optional.of(lista))
                    : ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/listar", produces = "application/json")
    public CtrlResponse<List<U>> listar(HttpServletRequest req) {
        return CtrlResponse.of(new ResponseEntity<>(getServicio().findAllAlive(), HttpStatus.OK));
    }

    @GetMapping(path = "/filtro", produces = "application/json")
    public CtrlResponse<Page<U>> filtrar(@RequestParam(defaultValue = "") String filtro, Pageable pageable, HttpServletRequest req) {
        return CtrlResponse.of(ResponseEntity.of(Optional.of(getServicio().findGeneral(filtro, pageable))));
    }

    @PostMapping(consumes = "application/json")
    public CtrlResponse<Object> add(@Valid @RequestBody U object, BindingResult result, HttpServletRequest req) throws IOException {
        if (result.hasErrors()) {
            return CtrlResponse.of(ResponseEntity.badRequest().body(getMessageMap(result)));
        } else {
            /**
             *  Agregado por Luis 16/12/2021
             */
            object = getServicio().normalizarDatos(object);
            Response resultado = getServicio().prePersist(object, null, Constantes.SYS_CAD_ACTION_ADD);

            if (resultado.success()) {
                U added = getServicio().persist(object);

                /* hago el refresh en el persist del service */
                added = getServicio().refresh(added);

                /**
                 *  Agregado por Luis 07/09/2021
                 */
                getServicio().postPersist(added, null, Constantes.SYS_CAD_ACTION_ADD);

                URI location = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(added.getId()).toUri();
                return CtrlResponse.of(ResponseEntity.created(location).body(added));
            } else {
                return CtrlResponse.of(ResponseEntity.badRequest().body(resultado));
            }
        }
    }

    @PutMapping(consumes = "application/json")
    public CtrlResponse<Object> update(@Valid @RequestBody U object, BindingResult result, HttpServletRequest req) throws IOException {
        if (result.hasErrors()) {
            return CtrlResponse.of(ResponseEntity.badRequest().body(getMessageMap(result)));
        } else {
            /**
             * Agregago por Luis
             * 08/09/2021
             * en "original" se guarda la version de la entidad antes de la actualizacion al solo efecto
             * de enviar al proceso de pre y post persist, a efectos de controles necesarios en cada caso.
             * Como Ejemplo la "autitoria del postpersist"
             */
            U original = this.recargarOriginal(object.getId());
            entityManager.detach(original);

            /**
             *  Agregado por Luis 16/12/2021
             */
            Response resultado = getServicio().prePersist(object, original, Constantes.SYS_CAD_ACTION_UPD);

            if (resultado.success()) {
                U updated = getServicio().persist(object);

                /* hago el refresh en el persist del service */
                updated = getServicio().refresh(updated);

                /**
                 *  Agregado por Luis 07/09/2021
                 */
                getServicio().postPersist(updated, original, Constantes.SYS_CAD_ACTION_UPD);

                return CtrlResponse.of(ResponseEntity.accepted().body(updated));
            } else {
                return CtrlResponse.of(ResponseEntity.badRequest().body(resultado));
            }
        }
    }

    @DeleteMapping(path = "/{key}")
    public CtrlResponse<Object> delete(@PathVariable("key") TKI key, HttpServletRequest req) throws IOException {
        /**
         *  Agregado por Luis 16/12/2021
         */
        U object = getServicio().findById(key).get();
        Response resultado = getServicio().prePersist(object, null, Constantes.SYS_CAD_ACTION_DEL);

        if (resultado.success()) {
            getServicio().findById(key).ifPresent(servicio::delete);

            /**
             *  Agregado por Luis 07/09/2021
             */
            getServicio().postPersist(object, null, "delete");

            return CtrlResponse.of(ResponseEntity.ok().build());
        } else {
            return CtrlResponse.of(ResponseEntity.badRequest().body(resultado));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public U recargarOriginal(TKI id) throws ServiceException {
        U instanciaOriginal = getServicio().findById(id).get();
        return instanciaOriginal;
    }
}
