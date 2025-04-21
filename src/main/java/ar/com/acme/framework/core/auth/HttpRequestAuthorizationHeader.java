package ar.gov.posadas.mbe.framework.core.auth;

import ar.gov.posadas.mbe.framework.core.http.EHttpAuthType;

public record HttpRequestAuthorizationHeader(EHttpAuthType type, String value) { }
