package ar.com.acme.framework.core.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Esta interface establece el comportamiento necesario para "engancharse" al mecanismo
 * de Autenticación de springboot, es decir para dar soporte efectivo a las operaciones
 * de autenticación. La implementación efectiva utilizará nuestro concepto de "ITOKEN"
 * uniendo asi ambos mundos.
 *
 * @author jmfragueiro
 * @version 20230601
 */
public interface IAuthenticationService extends AuthenticationManager {
    Authentication authenticateFromRequest(HttpServletRequest request) throws AuthenticationException;
    
    RequestMatcher getPublicPaths();

    boolean thisRequestRequireAuthentication(HttpServletRequest request);
}
