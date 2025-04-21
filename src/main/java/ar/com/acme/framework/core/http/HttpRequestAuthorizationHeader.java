package ar.com.acme.framework.core.http;

public record HttpRequestAuthorizationHeader(EHttpAuthType type, String value) { }
