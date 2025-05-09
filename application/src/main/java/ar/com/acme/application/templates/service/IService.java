package ar.com.acme.application.templates.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ar.com.acme.application.templates.entity.EntityException;
import ar.com.acme.application.templates.entity.IEntity;
import ar.com.acme.application.templates.repository.IRepository;

/**
 * <p>Esta interfase representa el comportamiento deseable de un servicio de capa de negocio para
 * la aplicación. Se respeta aquí el esquema de dependencia de:</p>
 *
 * Entidad ({@code IEntidad}) <- Repositorio ({@code IRepository}) <- ({@code IService}) Servicio <- Controlador ({@code IController})
 *
 * <p>Todo servico debe brindar una funcionalidad mínima que permita soportar el trabajo de los
 * controladores y abstraer 'hacia arriba' cualquier impleemntación de una {@link IEntidad}.
 * Esta interfase debe servir como una plantilla para la generación de clases específicas para
 * cada entidad del modelo de negocio.</p>
 *
 * @param <U>   El tipo de la entidad servida
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20250505
 */
public interface IService<U extends IEntity<TKI>, TKI> extends Serializable, Cloneable {
    /**
     * Todas las implementaciones de un Servicio de modelo de negocio deben tener un repositorio
     * por detrás que es el que efectivamente se comunica con el mecanismo de pérsistencia elegido.
     * Este es el metodo que debe utilizarse para obtener una referencia a ese {@link IRepositorio}.
     *
     * @return una referencia al repositorio subyacente al servicio de modelo de negocio.
     */
    IRepository<U, TKI> getRepo();

    /**
     * Este metodo debe establecer el hecho de que la instancia pasada como argumento ha sido seleccionada
     * para ser persistida, pero, sin embargo, el acto efectivo de la persistencia podria demorarse en el
     * tiempo por cuestiones del mecanismo real de persistencia considerado, como por ejemplo si se trabaja
     * contra una base de datos con un esquema de transacciones y la llamada al metodo se encuentra dentro
     * de una transaccion mayor que aún no ha sido comiteada. Si la instancia ya esta persistida se vuelve
     * persistir en su estado actual (seria como un update). Si existe algun problema con la accion deberia
     * lanzarse una excepcion de persistencia.
     *
     * @param instancia la instancia a ser persistida.
     * @return la propia instancia persistida (o marcada como tal).
     */
    U persist(U instancia) throws ServiceException;

    /**
     * Este metodo deberia 'eliminar' una instancia de una entidad persistente del repositorio de entidades
     * persistentes. Dentro del framework, lo que se entienda por eliminar deberá estar dado por, AL MENOS,
     * una combinación de dos acciones:
     * A) 'desactivar la instancia': vía su metodo delete() (ver {@code delete()} en {@link IEntidad})
     * B) 'comunicar al motor de persistencia': haciendo persistente el cambio de estado de dicha instancia.
     * Si existe algun problema con la accion, deberia lanzarse una excepcion de persistencia.
     */
    void delete(U instancia) throws ServiceException;

    /**
     * Este metodo devuelve un objeto identificable del tipo T a partir de su identificación interna. Si existe
     * algun problema deberia lanzar una excepcion de entidad.
     *
     * @param id el identificador interno del objeto deseado.
     * @returns una instancia del objeto cuto identificador coincide con el pasado como argumento.
     */
    Optional<U> findById(TKI id) throws EntityException;

    /**
     * Este metodo devuelve todos los objetos identificables del tipo T que se encuentren 'vivos'.
     * El significado de 'vivo' debería tener sentido para la aplicacion y deberia aplicarse aquí
     * en funcion de lo que dictamine el metodo isAlive() de la entidad. Si existe algun problema
     * deberia lanzar una excepcion de entidad.
     *
     * @returns La colección de objetos vivos de tipo T obtenidos (sin filtros).
     */
    List<U> findAllAlive() throws EntityException;
}
