package ar.com.acme.framework.core.auth.types;

import ar.com.acme.framework.core.auth.IAuthenticationHelper;
import ar.com.acme.framework.core.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationType {
    TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad);
}
