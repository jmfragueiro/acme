package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Tools;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.http.HttpResponseBody;
import ar.com.acme.framework.core.session.ISessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constantes.SYS_CAD_AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final ISessionService sessionService;

    @PostMapping(value = Constantes.SYS_CAD_LOGGIN_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpResponseBody> loggin(HttpServletRequest request, String username, String password) {
        try {
            var authtenticationToken = authenticationService.authenticateFromLogginRequest(request, username, password);

            var logginSuccessJws = sessionService.loggin(authtenticationToken);

            return ResponseEntity.ok(
                    new HttpResponseBody(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK,
                        Constantes.MSJ_SES_INF_LOGGON,
                        logginSuccessJws));
        } catch (Exception e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(Constantes.MSJ_SES_ERR_LOGIN, e.getMessage(), username));
        }
    }

    @PostMapping(Constantes.SYS_CAD_LOGGOUT_URL)
    public ResponseEntity<HttpResponseBody> logout(HttpServletRequest request) {
        try {
            var authtenticationToken = authenticationService.authenticateFromLoggedRequest(request);

            var loggoutSuccessJws = sessionService.loggout(authtenticationToken);

            return ResponseEntity.ok(
                    new HttpResponseBody(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK,
                        loggoutSuccessJws,
                        Constantes.MSJ_SES_INF_LOGGOFF));
        } catch (Exception e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(Constantes.MSJ_SES_ERR_LOGOFF, e.getMessage(), null));
        }
    }
}
