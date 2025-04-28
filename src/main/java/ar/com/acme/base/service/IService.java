package ar.com.acme.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ar.com.acme.base.entity.EntityException;
import ar.com.acme.base.entity.IEntity;
import ar.com.acme.base.repository.IRepository;

/**
 * Esta interfase representa el comprotamiento deseable de un servicio de persistencia para el modelo
 * de datos del sistema. Hereda aqui de IRepositorioAuditable porque se trabaja con entidades auditadas.
 * Las imlementaciones se espera que brinden los servs necesarios para persistir cambios al motor
 * de persistencia, eliminar instancias de entidades del motor y alplicar los mecanismos de auditoria,
 * siempre abtrayendo de las cuestiones 'fisicas' (de la implementacion concreta) al sistema en si.
 *
 * @param <U>   El tipo de la entidad servida
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public interface IService<U extends IEntity<TKI>, TKI> extends Serializable, Cloneable {
    /**
     * Todas las implementaciones de Servicio de Repositorio deben tener un repositorio
     * por detrás que es el que efectivamente se comunica con la base de datos. Este es
     * el metodo que debe utilizarse para obtener una referencia a ese repositorio.
     *
     * @return una referencia al repositorio subyacente al servicio de repositorio.
     */
    IRepository<U, TKI> getRepo();

    /**
     * Este metodo debe establecer el hecho de que la instancia pasada como argumento ha sido seleccionada
     * para ser persistida, pero sin embargo, el acto efectivo de la persistencia podria demorarse en el
     * tiempo por cuestiones del mecanismo real de persistencia considerado, como por ejemplo si se trabaja
     * contra una base de datos con un esquema de transacciones y la llamada al metodo se encuentra dentro
     * de una transaccion mayor que aún no ha sido comiteada. Si la instancia ya esta persistida se vuelve
     * persistir en su estado actual (seria como un update). Si existe algun problema con la accion deberia
     * lanzarse una excepcion de persistencia.
     *
     * @param instancia la instancia a ser persistida.
     * @return Retorna la propia instancia persistida (o marcada como tal).
     */
    U persist(U instancia) throws ServiceException;

    /**
     * Este metodo deberia 'eliminar' una instancia de una entidad persistente del repositorio de entidades
     * persistentes. Dentro del framework, lo que se entienda por eliminar deberá estar dado por, AL MENOS,
     * una combinación de dos acciones:
     * A) 'desactivar la instancia': vía su metodo delete() (ver delete() en Entidad)
     * B) 'comunicar al motor de persistencia': haciendo persistente el cambio de estado de dicha instancia.
     * Si existe algun problema con la accion, deberia lanzarse una excepcion de persistencia.
     *
     * @param instancia la instancia a ser eliminada.
     */
    void delete(U instancia) throws ServiceException;

    /**
     * Este metodo devuelve un objeto identificable del tipo T a partir de su ID interno. Si existe
     * algun problema deberia lanzar una excepcion de entidad.
     *
     * @param id El ID interno del objeto (registro) de tipo T deseado.
     * @returns El objeto de tipo T que cuyo ID coincide con el pasado.
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
