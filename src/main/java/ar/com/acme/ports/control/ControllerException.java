package ar.com.acme.ports.control;

import ar.com.acme.framework.core.exception.MessageException;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con un controlador del sistema, sea de conectividad, acceso o de cualquier tipo de verificacione que
 * se asocie a las entidades manejadas.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class ControllerException extends MessageException {
    public ControllerException(String mensaje) {
        super(mensaje);
    }

    public ControllerException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
