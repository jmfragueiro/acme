package ar.com.acme.framework.core.http;

import ar.com.acme.framework.core.auth.AuthenticationType;

public record HttpRequestAuthorizationHeader(AuthenticationType type, String value) { }
