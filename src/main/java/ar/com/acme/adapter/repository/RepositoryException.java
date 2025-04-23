package ar.com.acme.adapter.repository;

import ar.com.acme.framework.core.exception.MessageException;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con un repositorio del sistema, sea de conectividad, acceso, del JPA subyacente o un problema lanzado
 * por el propio driver de dicho motor.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class RepositoryException extends MessageException {
    public RepositoryException(String mensaje) {
        super(mensaje);
    }

    public RepositoryException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
