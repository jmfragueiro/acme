package ar.gov.posadas.mbe.ports.service;

import ar.gov.posadas.mbe.framework.common.Response;
import ar.gov.posadas.mbe.ports.entity.EntityException;
import ar.gov.posadas.mbe.ports.entity.IEntidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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
public interface IServicio<U extends IEntidad<TKI>, TKI> extends Serializable, Cloneable {
    /**
     * Toda instancia de un servicio debe poder expresar el nombre simple de la Clase de la Entidad que se
     * encuentra bajo su control, lo que puede ser usado por distintas herramientas o vistas dentro del proceso
     * de implementacion. Este metodo debe entonces retornar el nombre de la clase concreta de la instancia que
     * esta siendo servida por este.
     *
     * @return El nombre de la clase concreta de la instancia servida.
     */
    String getNombreEntidadAsociada();

    /**
     * Todas las implementaciones de Servicio de Repositorio deben tener un repositorio
     * por detrás que es el que efectivamente se comunica con la base de datos. Este es
     * el metodo que debe utilizarse para obtener una referencia a ese repositorio.
     *
     * @return una referencia al repositorio subyacente al servicio de repositorio.
     */
    //IRepositorio<U, TKI> getRepo();

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
     * Este metodo deberia realizar un "reinicio" de un objeto persistido contra el mecanismo de persistencia
     * para asegurarse de que lo que se "esta viendo" (en memoria) es realmente lo ultimisimo persisitdo.
     *
     * @param instancia la instancia a ser refrescada.
     * @return Retorna la propia instancia refrescada.
     */
    U refresh(U instancia) throws ServiceException;

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

    /**
     * Este metodo debe encargarse de calcular y retornar la cantidad total de instancias de U almacenadas
     * dentro del repositorio. Aqui solo deben incluirse las instancias que estan 'vivas' (isAlive()=true).
     * Si existe algun problema con la accion deberia lanzarse una excepcion de entidad.
     *
     * @returns Retorna la cantidad de instancias eliminadas
     */
    Long countAllAlive() throws EntityException;

    /**
     * Este metodo permite ralizar una normalizacion de los datos de una instancia antes de ser persistidos.
     * Este metodo sera llamado siempre antes de la persistencia final de una instancia y se espera tambien
     * aqui se realicen todos los controles previos necesarios (que no hayan podido realizarse antes por la
     * razón que sea).
     *
     * @param instancia la instancia con los datos a ser persistidos
     * @return la instancia con los datos normalizados (lo que sea que signifique en cada caso)
     */
    U normalizarDatos(U instancia);

    /**
     * Este metodo se ejecuta con anterioridad al persist de la Entidad y eventualmente es usado para validar
     * los datos a persistir. El parametro "metodo" viene cargado desde el controlador base, puede recibir add,
     * update, delete ... o cualquier otro indicador para que la implementacion de prePersist, en los services
     * que implementan esta clase, sepa que hacer.
     *
     * @param instancia la instancia con los datos a ser persistidos
     * @param instanciaoriginal la instancia original con los datos antes de la modificación
     * @param metodo el metodo a utilizarse que ejecuta los cambios a persistirse
     * @return el resultado de la operación de prepersist (lo que sea que signifique en cada caso)
     */
    Response prePersist(U instancia, U instanciaoriginal, String metodo);

    /**
     * Este metodo se ejecuta con anterioridad al persist de la Entidad y eventualmente es usado para validar
     * los datos a persistir. El parametro "metodo" viene cargado desde el controlador base, puede recibir add,
     * update, delete ... o cualquier otro indicador para que la implementacion de prePersist, en los services
     * que implementan esta clase, sepa que hacer.
     *
     * @param instancia la instancia con los datos a ser persistidos
     * @param instanciaoriginal la instancia original con los datos antes de la modificación
     * @param metodo el metodo a utilizarse que ejecuta los cambios a persistirse
     * @return el resultado de la operación de prepersist (lo que sea que signifique en cada caso)
     */
    void postPersist(U instancia, U instanciaoriginal, String metodo) throws IOException;

    /**
     * Este metodo devuelve un objeto Page como resultado de una busqueda general
     * con un filtro determinado (pasado como una cadena con un formato que cada
     * implementacion debera conocer y utilizar), de manera de permitir paginacion
     * contra una busqueda particular y a partir de un objeto Pageable que tiene
     * la informacion sobre que pagina obtener. NOTA: a efectos de compatibilidad,
     * la implementación por defecto deberia traer todos los objetos vivos, sin
     * preocuparse por el filtro.
     *
     * @param filtro la cadena de filtro a interpretarse
     * @param pager el objeto pageable para tomar como base
     * @returns El objeto Page con la pagina de datos resultado
     */
    Page<U> findGeneral(String filtro, Pageable pager);
}
