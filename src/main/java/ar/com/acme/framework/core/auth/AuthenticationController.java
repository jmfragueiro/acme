package ar.gov.posadas.mbe.framework.core.auth;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Response;
import ar.gov.posadas.mbe.framework.common.Tools;
import ar.gov.posadas.mbe.framework.core.exception.AuthException;
import ar.gov.posadas.mbe.framework.core.http.EHttpAuthType;
import ar.gov.posadas.mbe.framework.core.http.HttpResponseBody;
import ar.gov.posadas.mbe.framework.core.session.ISessionService;
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
                        HttpStatus.CREATED,
                        null,
                        logginSuccessJws,
                        Constantes.MSJ_SES_INF_LOGGON,
                        request.getServletPath(),
                        EHttpAuthType.BEARER));
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
                        null,
                        loggoutSuccessJws,
                        Constantes.MSJ_SES_INF_LOGGOFF,
                        request.getServletPath(),
                        EHttpAuthType.BEARER));
        } catch (Exception e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(Constantes.MSJ_SES_ERR_LOGOFF, e.getMessage(), Constantes.SYS_CAD_UNKNOW));
        }
    }

    @PostMapping(value = Constantes.SYS_CAD_GETTOKEN_URL)
    public ResponseEntity<?> jwtByUsername(HttpServletRequest request, Long userId, String controlFecha) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Response.fail(Constantes.MSJ_SEC_ERR_NOT_IMPLEMENTED));
    }
}
