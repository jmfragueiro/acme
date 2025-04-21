package ar.gov.posadas.mbe.impldefault;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Propiedades;
import ar.gov.posadas.mbe.framework.core.auth.HttpRequestAuthorizationHeader;
import ar.gov.posadas.mbe.framework.core.exception.AuthException;
import ar.gov.posadas.mbe.framework.core.http.EHttpAuthType;
import ar.gov.posadas.mbe.framework.core.http.IHttpRequestAuthorizationValueDecoder;
import ar.gov.posadas.mbe.framework.core.jws.IJwsService;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class DefaultHttpRequestAuthorizationValueDecoder implements IHttpRequestAuthorizationValueDecoder {
    private final String clientid;
    private final String clientsecret;
    private final IJwsService jwsService;

    public DefaultHttpRequestAuthorizationValueDecoder(Propiedades propiedades, IJwsService jwsService) {
        this.clientid = propiedades.getSecurity().get("jwt_client-id");
        this.clientsecret = propiedades.getSecurity().get("jwt_client-secret");
        this.jwsService = jwsService;
    }

    @Override
    public String getValidAuthorizationValueFromRequest(HttpServletRequest request) {
        var authCad = getAuthorizationValueFromRequest(request);

        validateAuthorizationValue(authCad);

        return authCad.value();
    }

    @Override
    public void validateAuthorizationValueFromRequest(HttpServletRequest request) {
        var authCad = getAuthorizationValueFromRequest(request);

        validateAuthorizationValue(authCad);
    }

    private void validateAuthorizationValue(HttpRequestAuthorizationHeader authCad) {
        switch (authCad.type()) {
            case BASIC -> validateBasicAuthCad(authCad.value());
            case BEARER -> validateBearerAuthCad(authCad.value());
            default -> throw new AuthException(Constantes.MSJ_SEC_ERR_BADREQUESTVALUE);
        };
    }

    private HttpRequestAuthorizationHeader getAuthorizationValueFromRequest(HttpServletRequest request) {
        String reqauth = request.getHeader(Constantes.SYS_APP_HTTP_AUTH_CAD);
        if (reqauth == null) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADREQUEST);
        }

        var split = reqauth.split(Constantes.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADREQUEST);
        }

        String authtype = split[0].trim();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADREQUEST);
        }

        var type = List.of(EHttpAuthType.values())
                .stream()
                .filter(e -> e.name().equalsIgnoreCase(authtype))
                .findFirst().orElseThrow(() -> new AuthException(Constantes.MSJ_SEC_ERR_BADREQUESTVALUE));

        return new HttpRequestAuthorizationHeader(type, authcad);
    }

    private void validateBasicAuthCad(String authcad) {
        ///////////////////////////////////////////////////////////////////
        // PARA VER/GENERAR CUAL ES EL CODIGO QUE DEBE UTILIZARSE: //
        // var coded = new String( //
        // Base64.getEncoder() //
        // .encode(clientid.concat(":") //
        // .concat(clientsecret) //
        // .getBytes())); //
        ///////////////////////////////////////////////////////////////////
        String authUser = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
        String authSecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
        if (!(authUser.equals(clientid) && authSecret.equals(clientsecret))) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADREQUESTVALUE);
        }
    }

    private void validateBearerAuthCad(String authcad) {
        jwsService.validateJws(authcad);
    }
}
