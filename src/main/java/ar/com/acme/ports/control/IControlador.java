package ar.com.acme.ports.control;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import ar.com.acme.ports.entity.IEntidad;
import ar.com.acme.ports.service.IServicio;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Esta interfase representa el comprotamiento deseable de un controlador de servicios REST
 * que permita asociar una vista con un servicio de repositorio de persistencia dentro del
 * framework ad-hoc.
 *
 * @param <U>   El tipo de la entidad servida por el servicio
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public interface IControlador<U extends IEntidad<TKI>, TKI> {
    /**
     * Todas las implementaciones de Controlador de Servicio deben tener un servicio
     * por detrás que es el que efectivamente se comunica con los repositorios. Este
     * es el metodo que debe utilizarse para obtener una referencia a ese servicio.
     *
     * @return una referencia al servicio subyacente al controlador.
     */
    IServicio<U, TKI> getServicio();

    /**
     * Toda instancia de un controlador debe poder expresar el nombre simple de la Clase de la Entidad qye se
     * encuentra bajo su control, lo que puede ser usado por distintas herramientas o vistas dentro del proceso
     * de implementacion. Este metodo debe entonces retornar el nombre de la clase concreata de la instancia que
     * esta siendo controlada por este.
     *
     * @return El nombre de la clase concreata de la instancia controlada.
     */
    String getNombreEntidadAsociada();

    /**
     * Toda instancia de un ayudante de controlador debe poder responder a un pedido REST
     * GET /KEY con un valor, y uno solo, del de la clave de búsqueda de la clase U. Aqui
     * 'KEY' representa una clave única por la cual obtener un objeto de la clase U y donde
     * la definición sobre el estado de vida del mismo aceptable se deja a la implementación
     * concreta de la clase.
     *
     * @param key un valor de clave de tipo TKI que representa a la clave por la cual encontrar al objeto a devolver.
     * @param req un objeto utilizado para dar información del requierimiento al sistema de seguridad.
     * @return El objeto de la clase U requerido (en HATEOAS).
     */
    CtrlResponse<U> viewdit(TKI key, HttpServletRequest req);

    /**
     * Toda instancia de un controlador debe poder responder a un pedido REST GET "puro"
     * retornando el listado total de entidades 'vivas', (depende del método isAlive()),
     * para lo cual este método debe encargarse de aplicar las funciones correspondientes
     * y teniendo en cuenta que la respuesta deve volver en formato JSON con los agregados
     * requeridos por la práctica HATEOAS.
     *
     * @param req un objeto utilizado para dar información del requierimiento al sistema de seguridad.
     * @return La colección JSON (en HATEOAS) de todos los objetos persistidos de la clase U 'vivos'.
     */
    CtrlResponse<Collection<U>> list(HttpServletRequest req);

    /**
     * Toda instancia de un controlador debe poder responder a un pedido REST GET "/listar"
     * retornando el listado total de entidades 'vivas', (depende del método isAlive()),
     * para lo cual este método debe encargarse de aplicar las funciones correspondientes,
     * pero a diferencia del pedido GET "puro" (ver el método 'list') aquí se deberá generar
     * la respuesta en formato JSON "simple" (no HATEOAS).
     *
     * @param req un objeto utilizado para dar información del requierimiento al sistema de seguridad.
     * @return La colección JSON de todos los objetos persistidos de la clase U 'vivos'.
     */
    CtrlResponse<List<U>> listar(HttpServletRequest req);

    /**
     * Toda instancia de un controlador debe poder responder a un pedido REST GET '/filtrar', con
     * condiciones de filtrado y ordenado, para lo cual este método deberá encargarse de aplicar las
     * funciones correspondientes apoyándose, se espera, en un 'Ayudante de Controlador' (un objeto
     * con el tipo IControladorHelper) que permita interpretar los parámetros y actuar en consecuencia.
     * El resultado esperado es una colección en formato JSON con los agregados requeridos por la
     * práctica HATEOAS.
     *
     * @param filtro   una cadena de condición que será interpretada a efectos de filtrar la consulta.
     * @param pageable un objeto pageable para definir tanto el orden como la pagina a devolverse.
     * @param req      un objeto utilizado para dar información del requierimiento al sistema de seguridad.
     * @return La colección (en HATEOAS) de objetos persistidos de la clase U 'vivos' que responden a
     * las condiciones pasadas y en función de la página y el orden solicitado.
     */
    CtrlResponse<Page<U>> filtrar(String filtro, Pageable pageable, HttpServletRequest req);

    /**
     * Toda instancia de un ayudante de controlador debe poder responder a un pedido REST
     * POST acompañado de un objeto, y uno solo, de la clase U que será entonces persistido
     * al mecanismo de persistencia subyacente. En caso de existir errores de validación u
     * otros asociados, la respuesta, además de devolver código HTTP de error, será cargada
     * generada a partir del objeto BindingResult pasado como parámetro. El objeto 'objeto'
     * debe ser pasado en formato JSON convertible a uno de la entidad U.
     *
     * @param objecto un objeto U que se intenta persistir dentro del framework.
     * @param result  un objeto BindingResult utilizado para devolver información sobre la operación.
     * @param req
     * @return El objeto de la clase U requerido (en HATEOAS).
     */
    CtrlResponse<Object> add(U objecto, BindingResult result, HttpServletRequest req) throws IOException;

    /**
     * Toda instancia de un ayudante de controlador debe poder responder a un pedido REST
     * PUT acompañado de un objeto, y uno solo, de la clase U, el cual se espera que ya
     * exista en el mecanismo de persistencia subyacente y cuyos datos serán actualizados
     * con los valores contenidos en el objeto argumento. En caso de existir errores de
     * validación u otros asociados, la respuesta, además de devolver un código HTTP de
     * error, será cargada a partir del objeto BindingResult pasado como parámetro. El
     * objeto 'objeto' debe ser pasado en formato JSON convertible a uno de la entidad U.
     *
     * @param objecto un objeto U que se intenta persistir dentro del framework.
     * @param result  un objeto BindingResult utilizado para devolver información sobre la operación.
     * @param req
     * @return El JSON del objeto de la clase U actualizado (en HATEOAS).
     */
    CtrlResponse<Object> update(U objecto, BindingResult result, HttpServletRequest req) throws IOException;

    /**
     * Toda instancia de un ayudante de controlador debe poder responder a un pedido REST
     * DELETE /KEY con un valor, y uno solo, del de la clave de búsqueda de la clase U. Aqui
     * 'KEY' representa una clave única por la cual encontrar al objeto de la clase U el cual
     * deberá ser marcado como 'no vivo' (es decir que a partir de esta operación, el valor
     * del método isAlive() para ese objeto deberá ser 'falso'). Se espera que el objeto se
     * encuentre 'vivo' (isAlive()=true) o de lo contrario no debería ser encontrado, lo que
     * debería devoveler un error en la respuesta HTTP.
     *
     * @param key un valor de clave de tipo TKI que representa a la clave por la cual encontrar al objeto a 'matar'.
     * @param req
     * @return El objeto de la clase U requerido (en HATEOAS).
     */
    CtrlResponse<Object> delete(TKI key, HttpServletRequest req) throws IOException;
}
