package ar.com.acme.framework.core.auth;

import ar.com.acme.framework.core.http.EHttpAuthType;

public record HttpRequestAuthorizationHeader(EHttpAuthType type, String value) { }
