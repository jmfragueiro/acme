package ar.com.acme.base.service;

import ar.com.acme.base.exception.MessageException;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con un servicio de repositorio del sistema, sea de acceso, de validación o un problema lanzado por el
 * el sistema implementado.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class ServiceException extends MessageException {
    public ServiceException(String mensaje) {
        super(mensaje);
    }

    public ServiceException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
