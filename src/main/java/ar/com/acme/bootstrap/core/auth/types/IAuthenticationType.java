package ar.com.acme.bootstrap.core.auth.types;

import ar.com.acme.bootstrap.core.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.core.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationType {
    TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad);
}
