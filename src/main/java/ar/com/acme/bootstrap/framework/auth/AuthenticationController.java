package ar.com.acme.bootstrap.framework.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.base.common.Tools;
import ar.com.acme.base.templates.controller.ControllerResponse;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.http.HttpResponseBody;
import ar.com.acme.bootstrap.framework.session.ISessionService;

@RestController
@RequestMapping(BootstrapConstants.SYS_CAD_AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final ISessionService sessionService;

    @PostMapping(value = BootstrapConstants.SYS_CAD_LOGGIN_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ControllerResponse<HttpResponseBody> login(HttpServletRequest request) {
        try {
            var authtentication = authenticationService.authenticateFromRequest(request);

            var loginSuccessJws = sessionService.login(authtentication);

            return ControllerResponse.of(
                    ResponseEntity.ok(
                        new HttpResponseBody(
                            LocalDateTime.now().toString(),
                            HttpStatus.OK,
                            BootstrapConstants.MSJ_SES_INF_LOGGON,
                            loginSuccessJws)));
        } catch (Exception e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(BootstrapConstants.MSJ_SES_ERR_LOGIN, e.getMessage(), request.getParameter("username")));
        }
    }

    @PostMapping(BootstrapConstants.SYS_CAD_LOGGOUT_URL)
    public ResponseEntity<HttpResponseBody> logout(HttpServletRequest request) {
        try {
            var authtentication = authenticationService.authenticateFromRequest(request);

            sessionService.logout(authtentication);

            return ResponseEntity.ok(
                    new HttpResponseBody(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK,
                        null,
                        BootstrapConstants.MSJ_SES_INF_LOGGOFF));
        } catch (Exception e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(BootstrapConstants.MSJ_SES_ERR_LOGOFF, e.getMessage(), null));
        }
    }
}
