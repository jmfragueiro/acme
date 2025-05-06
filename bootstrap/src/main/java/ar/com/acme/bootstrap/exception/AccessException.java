package ar.com.acme.bootstrap.exception;

import org.springframework.security.access.AccessDeniedException;

import ar.com.acme.commons.Logging;
import ar.com.acme.commons.Constants;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con algú componente de seguridad del sistema, sea de acceso, validación o un problema lanzado por el
 * sistema implementado.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class AccessException extends AccessDeniedException {
    /**
     * Esta version del contructor permite crear una excepcion sin mensaje ni datos extras
     * (utiliza en este caso un mensaje generico tomado de la constante MSJ_ERR_EXCEPCION).
     *
     * @see Constants
     */
    public AccessException() {
        this(Constants.MSJ_USR_ERR_NOACCES);
    }

    /**
     * Esta version del contructor permite crear una excepcion con mensaje y sin datos extras.
     *
     * @param mensaje El mensaje que describe la excepcion.
     */
    public AccessException(String mensaje) {
        this(mensaje, null);
    }

    /**
     * Esta version del contructor permite crear una excepcion con mensaje y con datos extras.
     *
     * @param mensaje El mensaje que describe la excepcion.
     * @param extra   La cadena con datos extras para mostrarInnerLayout en la exepcion
     */
    public AccessException(String mensaje, String extra) {
        super(mensaje);
        registrarMensaje(mensaje, extra);
    }

    /**
     * Este metodo registra al sistema de Logging la excepcion lanzada
     */
    private void registrarMensaje(String mensaje, String extra) {
        Logging.error(this.getClass(), extra);
    }
}