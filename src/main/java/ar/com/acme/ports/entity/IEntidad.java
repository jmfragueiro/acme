package ar.gov.posadas.mbe.ports.entity;

import java.time.LocalDateTime;

/**
 * Esta interface representa el ultimo escalon predefinido por el framework ad-hoc, para las entidades
 * que pueden ser representadas dentro del sistema. Se utiliza basicamente con fines de abstraccion y
 * permite agregar los ultimos mensajes aceptados por la jerarquia.
 *
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public interface IEntidad<TKI> {
    /**
     * Identificador unico (como una clave primaria) para los objetos de una entidad identificable. Todos
     * los objetos de una entidad identificable deben poseer un identificador único de tipo long int. La
     * entidad debe establecer un mecanismo automatico de asignacion de ID, de manera de que no existe un
     * seter para este atributo.
     */
    TKI getId();

    /**
     * Toda entidad identificable debe poseer un modo de determinar si una instancia se encuentra 'viva', o
     * 'activa', o no (por ejemplo, pudiendo ser descartada si fuera necesario). La definicion de viva o de
     * activa depende de la aplicacion y aqui solamente se presenta el mecanismo para determinarla. Es este
     * metodo el encargado de anunciar si la instancia de la entidad identificable en cuestion se encuentra
     * 'viva' o no, sea lo que fuere que eso signifique. Permite establecer un patron comun para determinar,
     * por ejemlo, objetos dados de baja.
     *
     * @return Retorna true si el objeto está 'vivo', o false si no lo esta.
     */
    boolean isAlive();

    /**
     * Toda entidad persistente debe poseer un modo de determinar si una instancia ya ha sido marcada para
     * ser persistida o aun se encuentra unicamente en memoria. Es este metodo el encargado de anunciar si la
     * instancia de la entidad persistente en cuestion se encuentra marcada para ser persistida o no. Recordar
     * que, bajo ciertas implementaciones, el hecho de que sea marcada para persistirse no significa que
     * efectivamente lo haya sido.
     *
     * @returns Retorna false si el objeto ya ha sido persistido, o true si no lo ha sido.
     */
    boolean isNew();

    /**
     * Toda entidad persistente debe poseer un modo de ejecutar ciertas acciones requeridas para ser persistida.
     * Es este metodo el encargado de ejecutar dichas acciones durante la persistencia de la instancia, de manera
     * de que, para el sistema, dicha instancia, con su estado al momento de llamar a este metodo, debe quedar
     * durable, es decir que debe poder recuperarse multiples veces en el futuro.
     */
    void onPersist();

    /**
     * Este metodo deberia desactivar a una instancia de una entidad identificable. Esto simplemente nos
     * dice que se espera que se implemente algun mecanismo que asegure que a partir de una llamada al
     * presente metodo, la instancia retorne el valor falso para el isAlive(). Si existe agun problema con
     * la accion, deberia lanzarse una excepcion de entidad.
     */
    void onDelete();

    /**
     * Este metodo deberia ser capaz de devolver la fecha en la que una instancia ha sido 'creada' y ha sido
     * efectivamente persistida o bien deberia devolver nulo unicamente si la instancia en cuestion no ha sido
     * persistida (si isNew() retorna true).
     *
     * @return Retorna la fecha de 'creacion' persitida de la instancia o null si es una nueva.
     */
    LocalDateTime getFechaalta();

    /**
     * Este metodo deberia ser capaz de devolver la fecha en la que una instancia ha sido modificada por ultima
     * vez, y cuyas modificaciones han sido efectivamente persistidas o bien deberia devolver nulo unicamente si
     * la instancia en cuestion no ha sido persistida (si isNew() retorna true).
     *
     * @return Retorna la fecha de ultima modificacion persitida de la instancia o null si es una nueva.
     */
    LocalDateTime getFechaumod();

    /**
     * Este metodo deberia ser capaz de devolver la fecha en la que una instancia ha sido modificada por ultima
     * vez, y cuyas modificaciones han sido efectivamente persistidas o bien deberia devolver nulo unicamente si
     * la instancia en cuestion no ha sido persistida (si isNew() retorna true).
     *
     * @return Retorna la fecha de ultima modificacion persitida de la instancia o null si es una nueva.
     */
    LocalDateTime getFechabaja();
}
