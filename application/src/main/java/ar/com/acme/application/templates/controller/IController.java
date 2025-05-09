package ar.com.acme.application.templates.controller;

import java.io.IOException;
import java.util.Collection;

import ar.com.acme.application.templates.entity.IEntity;
import ar.com.acme.application.templates.service.IService;

/**
 * <p>Esta interfase representa el comportamiento general deseable de un controlador básico
 * de servicios REST dentro de la arquitectura base de esta aplicación.
 * Se respeta aquí el esquema de dependencia de:</p>
 *
 * Entidad ({@code IEntidad}) <- Repositorio ({@code IRepository}) <- ({@code IService}) Servicio <- Controlador ({@code IController})
 *
 * <p>Esta interfase funciona entonces como una plantilla para estos controladores básicos,
 * permitiendo unificar funcionalidad general y ordenar la organización del código subyacente.
 * Cada controlador basado en esta plantilla procesará peticiones REST asociadas a una sola
 * {@link IEntidad} y la relacionará con un modelo de ingreso de datos (representado por la
 * clase argumento del parámetro de clase {@code WI}) y un modelo de generación de respuesta
 * (representado por la clase argumento del parametro de clase {@code WO}). Asi mismo, si se
 * requieren mas modelos y conversiones, debe resolverse en cada implementación específica.</p>
 *
 * <p> Todo {@code IController} debe tener un {@link IService} por detrás que es el que
 * procesa efectivamente las reglas de negocio que dan soporte al controlador.
 *
 * @param <U>    El tipo de la entidad procesada por el controlador
 * @param <TKI>  El tipo de la clave de identificacion para la entidad
 * @param <WI>   El tipo del modelo web de puerto primario asociado a la entidad
 * @param <WO>   El tipo del modelo web de puerto secundario asociado a la entidad
 * @author jmfragueiro
 * @version 20250505
 */
public interface IController<U extends IEntity<TKI>, TKI, WI, WO> {
    IService<U, TKI> getService();

    WO view(TKI key);

    Collection<WO> list();

    WO add(WI objeto) throws IOException;

    WO update(WI objeto) throws IOException;

    void delete(TKI key) throws IOException;

    WO toWebOutModel(U source);

    U fromWebInModel(WI source);
}
