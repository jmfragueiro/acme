package ar.com.acme.bootstrap.framework.http;

import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;

public record HttpRequestAuthorizationHeader(String type, String value) {
    public static HttpRequestAuthorizationHeader from(HttpServletRequest request) {
        String reqauth = request.getHeader(BootstrapConstants.SYS_CAD_HTTP_AUTH);
        if (reqauth == null) {
            throw new AuthException(BootstrapConstants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        var split = reqauth.split(BootstrapConstants.SYS_CAD_SPACE);
        if (split.length < 2) {
            throw new AuthException(BootstrapConstants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        String authtype = split[0].trim().toUpperCase();
        String authcad = split[1].trim();

        if (authtype.isBlank() || authcad.isBlank()) {
            throw new AuthException(BootstrapConstants.MSJ_REQ_ERR_BADREQUEST, request.getServletPath());
        }

        return new HttpRequestAuthorizationHeader(authtype, authcad);
    }
}
