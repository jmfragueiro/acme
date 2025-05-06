package ar.com.acme.bootstrap.http;

import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;

public record HttpRequestAuthorizationHeader(String type, String value) {
    public static HttpRequestAuthorizationHeader from(HttpServletRequest request) {
        String reqauth = request.getHeader(Constants.SYS_CAD_HTTP_AUTH);
        if (reqauth == null) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        var split = reqauth.split(Constants.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        String authtype = split[0].trim().toUpperCase();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        return new HttpRequestAuthorizationHeader(authtype, authcad);
    }
}
