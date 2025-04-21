package ar.com.acme.framework.core.http;

import jakarta.servlet.http.HttpServletRequest;

public interface IHttpRequestAuthorizationValueDecoder {
    String getValidAuthorizationValueFromRequest(HttpServletRequest request);

    void validateAuthorizationValueFromRequest(HttpServletRequest request);
}
