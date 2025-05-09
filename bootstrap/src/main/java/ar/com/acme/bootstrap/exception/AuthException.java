package ar.com.acme.bootstrap.exception;

import org.springframework.security.core.AuthenticationException;

import ar.com.acme.commons.Logging;
import ar.com.acme.commons.Tools;
import ar.com.acme.commons.Constants;

/**
 * Esta clase de excepcion deberia utilizarse para representar todos los errores asociados a un problema
 * con algún componente de autenticación del sistema, sea de acceso, validación o un problema lanzado por
 * el sistema implementado.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public class AuthException extends AuthenticationException {
    public AuthException() {
        this(Constants.MSJ_SES_ERR_USERNOAUTH);
    }

    public AuthException(String mensaje) {
        this(mensaje, null);
    }

    public AuthException(String mensaje, String extra) {
        super(mensaje);
        registrarMensaje(mensaje, extra);
    }

    private void registrarMensaje(String mensaje, String extra) {
        Logging.error(this.getClass(), Tools.getCadenaErrorFormateada(mensaje, extra, null));
    }
}