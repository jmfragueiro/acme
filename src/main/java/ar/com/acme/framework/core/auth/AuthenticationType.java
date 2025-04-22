package ar.com.acme.framework.core.auth;

import java.util.Base64;
import java.util.List;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.http.HttpRequestAuthorizationHeader;
import ar.com.acme.framework.core.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

public enum AuthenticationType {
    BASIC {
        @Override
        public TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            ///////////////////////////////////////////////////////////////////
            // PARA VER/GENERAR CUAL ES EL CODIGO QUE DEBE UTILIZARSE:       //
            // var coded = new String(                                       //
            //                    Base64.getEncoder()                        //
            //                          .encode(clientid.concat(":")         //
            //                          .concat(clientsecret)                //
            //                          .getBytes()));                       //
            ///////////////////////////////////////////////////////////////////
            String authUser = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
            String authSecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
            if (!(authUser.equals(authHelper.getClientid()) && authSecret.equals(authHelper.getClientsecret()))) {
                throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUESTVALUE);
            }

            var repoUser = authHelper.getPrincipalService()
                                               .findByName(username)
                                               .orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL));

            return new TokenAuthentication(repoUser, password);
        }
    },
    BEARER {
        @Override
        public TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            authHelper.getJwsService().validateJws(authcad);

            var token = authHelper.getJwsService().getIdFromJws(authcad);

            var repoUser = authHelper.getPrincipalService()
                                               .findByToken(token)
                                               .orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_NOACTIVETOKEN));

            return new TokenAuthentication(repoUser);
        }
    };

    public abstract TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad);

    public static HttpRequestAuthorizationHeader getAuthorizationValueFromRequest(HttpServletRequest request) {
        String reqauth = request.getHeader(Constantes.SYS_CAD_HTTP_AUTH);
        if (reqauth == null) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        var split = reqauth.split(Constantes.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        String authtype = split[0].trim();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUEST);
        }

        var type = List.of(AuthenticationType.values())
                                             .stream()
                                             .filter(e -> e.name().equalsIgnoreCase(authtype))
                                             .findFirst().orElseThrow(() -> new AuthException(Constantes.MSJ_REQ_ERR_BADREQUESTVALUE));

        return new HttpRequestAuthorizationHeader(type, authcad);
    }
}
