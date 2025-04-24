package ar.com.acme.adapter.controller;

import java.io.IOException;
import java.util.Collection;

import ar.com.acme.adapter.entity.IEntity;
import ar.com.acme.adapter.service.IService;

/**
 * Esta interfase representa el comprotamiento deseable de un controlador de servicios REST
 * que permita asociar una vista con un servicio de repositorio de persistencia dentro del
 * framework ad-hoc.
 *
 * @param <U>   El tipo de la entidad servida por el servicio
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @param <W>   El tipo del modelo web asociado a la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public interface IController<U extends IEntity<TKI>, TKI, X, W> {
    /**
     * Todas las implementaciones de Controlador de entrada deben tener un repositorio
     * por detrás que es el que efectivamente se comunica con el mecanismo de persistenia.
     * Este es el metodo que debe utilizarse para obtener una referencia a ese repositorio.
     *
     * @return una referencia al repositorio subyacente al controlador.
     */
    IService<U, TKI> getService();

    ControllerResponse<W> view(TKI key);

    ControllerResponse<Collection<W>> list();

    ControllerResponse<W> add(X objeto) throws IOException;

    ControllerResponse<W> update(X objeto) throws IOException;

    ControllerResponse<Object> delete(TKI key) throws IOException;
}
