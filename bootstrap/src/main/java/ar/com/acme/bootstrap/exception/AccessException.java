package ar.com.acme.bootstrap.exception;

import org.springframework.security.access.AccessDeniedException;

import ar.com.acme.commons.Logging;
import ar.com.acme.commons.Tools;
import ar.com.acme.commons.Constants;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con algú componente de seguridad de acceso a recursos del sistema, sea de acceso, validación o por un
 * problema lanzado por el sistema implementado.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public class AccessException extends AccessDeniedException {
    public AccessException() {
        this(Constants.MSJ_SES_ERR_NOACCES);
    }

    public AccessException(String mensaje) {
        this(mensaje, null);
    }

    public AccessException(String mensaje, String extra) {
        super(mensaje);
        registrarMensaje(mensaje, extra);
    }

    private void registrarMensaje(String mensaje, String extra) {
        Logging.error(this.getClass(), Tools.getCadenaErrorFormateada(mensaje, extra, null));
    }
}