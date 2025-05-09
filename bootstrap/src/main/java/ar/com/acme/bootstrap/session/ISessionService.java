package ar.com.acme.bootstrap.session;

import org.springframework.security.core.Authentication;

/**
 * Esta interface establece el comportamiento necesario para gestionar las sesiones
 * de usuario dentro de una aplicación. Basicamente permitie registar el inicio
 * de una sesión con el login y terminar la misma vía el logout. Para todas sus ops
 * se basa en un "ticket" de autenticacion representado por la interfase Authentication
 * de Spring, de manera de enganchar bien con elmecanismo de seguridad de dicho framework.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public interface ISessionService {
    /**
     * Registra el inicio de una sesión de usuario a partir de un "ticket" de Autenticación.
     * En esta aplicación se espera que el método genere un JSON Web Signature (JWS) con
     * información sobre la sesión iniciada.
     *
     * @return una cadena JWS con información sobre la sesión iniciada.
     */
    String login(Authentication authentication);

    void logout(Authentication authentication);
}
