package ar.com.acme.application.templates.entity;

import java.time.LocalDateTime;

/**
 * <p>Esta interface representa el comportamiento deseable para el último escalón definido por
 * este diseño ad-hoc para la aplicación: el de las entidades del modelo. Se espera se utilize
 * basicamente con fines de abstraccion y permite agregar los ultimos mensajes aceptados por
 * la jerarquia.</p>
 *
 * <p>Toda entidad debe poseer un modo de determinar si una instancia de la misma ya ha sido
 * 'registrda': es decir que persistirá mas allá de una sesión de acceso a la aplicación, o
 * bien si unicamente se encuentra instanciada en memoria pero sin capacidad de persistencia
 * entre sesiones.<p>
 *
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20250505
 */
public interface IEntity<TKI> {
    /**
     * Es este metodo el encargado de anunciar si la instancia de la entidad en cuestion se
     * encuentra 'registrada', es decir marcada para ser persistir entre sesiones de acceso
     * a la aplicación o no.
     *
     * @returns {@code false} si el objeto ya ha sido registrado, o {@code true} si no lo ha sido.
     */
    boolean isNew();

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
     * Este metodo deberia ser capaz de 'desactivar' una instancia de esta entidad y devolver la fecha en la que
     * se produce dicha desactivacion. Esto implica que, posterior a la invocacion de este metodo, la instancia
     * debe retornar {@code false} al método {@code isAlive()}.
     *
     * @return fecha de 'desactivacion' persitida de la instancia.
     */
    LocalDateTime kill();

    /**
     * Todos los objetos {@code IEntidad} deben poder ser 'identificables' en el contexto del mecanismo de
     * registración elegido (por ejemplo el repositorio). Cada implementación debe establecer un mecanismo
     * automatico de asignacion de este valor de identificación , de manera de que no exista un seter para
     * este atributo. Este método debe retornar el identificador único para la instancia que recibe el mensaje.
     *
     * @return el identificador único para la instancia
     */
    TKI getId();

    /**
     * Este metodo deberia ser capaz de devolver la fecha en la que una instancia ha sido 'creada' y ha sido
     * efectivamente 'registrada' como tal o bien deberia devolver {@code null} unicamente si la instancia en
     * cuestion no ha sido aún registrada (si isNew() retorna true).
     *
     * @return Retorna la fecha de 'registración' de la instancia o {@code null} si es una nueva.
     */
    LocalDateTime getCreated();

    /**
     * Este metodo deberia ser capaz de devolver la fecha en la que una instancia ha sido modificada por ultima
     * vez, y cuyas modificaciones han sido efectivamente persistidas o bien deberia devolver nulo unicamente si
     * la instancia en cuestion no ha sido persistida (si isNew() retorna true).
     *
     * @return Retorna la fecha de ultima modificacion persitida de la instancia o null si es una nueva.
     */
    LocalDateTime getModified();
}
