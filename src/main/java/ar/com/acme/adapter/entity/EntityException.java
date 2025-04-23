package ar.com.acme.adapter.entity;

import ar.com.acme.bootstrap.core.exception.MessageException;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con alguna de las propiedades basicas de una entidad dentro del framework, los que puede asociarse a
 * operaciones propias de la entidad y mas alla de cualquier otro contexto interviniente.
 *
 * @author jmfragueiro
 * @version 20161011
 */
public class EntityException extends MessageException {
    public EntityException(String mensaje) {
        super(mensaje);
    }

    public EntityException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
