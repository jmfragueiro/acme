package ar.gov.posadas.mbe.framework.core.session;

import org.springframework.security.core.Authentication;

/**
 * Esta interface establece el comportamiento necesario para gestionar las sesiones
 * de usuario dentro de este miniframework. Basicamente permitie registar el inicio
 * de una sesión con el login y terminar la misma vía el logout. Para todas sus ops
 * se basa en un "ticket" de autenticacion representado por la interfase Authentication
 * de spring.
 *
 * @author jmfragueiro
 * @version 20250108
 */
public interface ISessionService {
    /**
     * Registra el inicio de una sesión de usuario a partir de un "ticket" de Autnenticación.
     * En este miniframework se espera que el método genere un JSON Web Signature (JWS) con
     * información sobre la sesión iniciada.
     *
     * @return una cadena JWS con información sobre la sesión iniciada.
     */
    String loggin(Authentication authentication);

    /**
     * Registra el final de una sesión de usuario a partir de un "ticket" de Autnenticación
     * que debería permitir encontrar dicha sesión en el lugar en el que fuera registada. Se
     * espera que el método genere un JSON Web Signature (JWS) con información sobre la
     * sesión finalizada.
     *
     * @return una cadena JWS con información sobre la sesión finalizada.
     */
    String loggout(Authentication authentication);
}
