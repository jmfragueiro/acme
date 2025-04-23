package ar.com.acme.bootstrap.core.exception;

import org.springframework.security.core.AuthenticationException;

import ar.com.acme.bootstrap.common.Constantes;
import ar.com.acme.bootstrap.common.Logging;
import ar.com.acme.bootstrap.common.Tools;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con algú componente de seguridad del sistema, sea de acceso, validación o un problema lanzado por el
 * sistema implementado.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class AuthException extends AuthenticationException {
    /**
     * Esta version del contructor permite crear una excepcion sin mensaje ni datos extras
     * (utiliza en este caso un mensaje generico tomado de la constante MSJ_ERR_EXCEPCION).
     *
     * @see Constants
     */
    public AuthException() {
        this(Constantes.MSJ_SES_ERR_USERNOAUTH);
    }

    /**
     * Esta version del contructor permite crear una excepcion con mensaje y sin datos extras.
     *
     * @param mensaje El mensaje que describe la excepcion.
     */
    public AuthException(String mensaje) {
        this(mensaje, null);
    }

    /**
     * Esta version del contructor permite crear una excepcion con mensaje y con datos extras.
     *
     * @param mensaje El mensaje que describe la excepcion.
     * @param extra   La cadena con datos extras para mostrarInnerLayout en la exepcion
     */
    public AuthException(String mensaje, String extra) {
        super(mensaje);
        registrarMensaje(mensaje, extra);
    }

    /**
     * Este metodo registra al sistema de Logging la excepcion lanzada
     */
    private void registrarMensaje(String mensaje, String extra) {
        Logging.error(this.getClass(),
                new StringBuilder(Constantes.SYS_CAD_OPENTYPE)
                        .append(Tools.getNombreMetodoLlamante(4))
                        .append(Constantes.SYS_CAD_CLOSETPE)
                        .append(mensaje)
                        .toString(),
                Tools.getEmptyStringOnNull(extra));
    }
}