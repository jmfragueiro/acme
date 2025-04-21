package ar.gov.posadas.mbe.framework.core.http;

import jakarta.servlet.http.HttpServletRequest;

public interface IHttpRequestAuthorizationValueDecoder {
    String getValidAuthorizationValueFromRequest(HttpServletRequest request);

    void validateAuthorizationValueFromRequest(HttpServletRequest request);
}
