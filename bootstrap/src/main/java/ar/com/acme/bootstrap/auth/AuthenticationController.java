package ar.com.acme.bootstrap.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.MessageException;
import ar.com.acme.commons.Tools;
import ar.com.acme.bootstrap.exception.AuthException;
import ar.com.acme.bootstrap.http.HttpRequestAuthorizationHeader;
import ar.com.acme.bootstrap.http.HttpResponseBody;
import ar.com.acme.bootstrap.jws.JWSException;
import ar.com.acme.bootstrap.session.ISessionService;

@RestController
@RequestMapping(Constants.SYS_CAD_AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final ISessionService sessionService;

    @PostMapping(value = Constants.SYS_CAD_LOGGIN_URL, consumes = Constants.SYS_CAD_APP_MIMETYPE_JSON)
    public HttpResponseBody login(HttpServletRequest request) {
        try {
            var authtentication = authenticationService.authenticateFromRequest(request);

            var loginSuccessJws = sessionService.login(authtentication);

            return new HttpResponseBody(LocalDateTime.now().toString(),
                                        HttpStatus.OK,
                                        Constants.MSJ_SES_INF_LOGGON,
                                        loginSuccessJws);
        } catch (JWSException e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(
                                            e.getMessage(),
                                            HttpRequestAuthorizationHeader.from(request).value(),
                                            null));
        } catch (AuthenticationException e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(
                                            Constants.MSJ_SES_ERR_LOGIN,
                                            e.getMessage(),
                                            request.getParameter("username")));
        } catch (Exception e) {
            throw new MessageException(Tools.getCadenaErrorFormateada(
                                                Constants.MSJ_SES_ERR_LOGIN,
                                                e.getMessage(),
                                                null));
        }
    }

    @PostMapping(Constants.SYS_CAD_LOGGOUT_URL)
    public HttpResponseBody logout(HttpServletRequest request) {
        try {
            var authtentication = authenticationService.authenticateFromRequest(request);

            sessionService.logout(authtentication);

            return new HttpResponseBody(LocalDateTime.now().toString(),
                                        HttpStatus.OK,
                                        Constants.MSJ_SES_INF_LOGGOFF,
                                        authtentication.getName());
        } catch (AuthenticationException e) {
            throw new AuthException(Tools.getCadenaErrorFormateada(
                                            Constants.MSJ_SES_ERR_LOGOFF,
                                            e.getMessage(),
                                            null));
        } catch (Exception e) {
            throw new MessageException(Tools.getCadenaErrorFormateada(
                                                Constants.MSJ_SES_ERR_LOGIN,
                                                e.getMessage(),
                                                null));
        }
    }
}
