package ar.gov.posadas.mbe.framework.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties(prefix = "frameworkmbe")
@Getter
public class Propiedades {
    private String entorno;
    private Map<String, String> security;
    private Map<String, String> token;
    private Map<String, String> extradata;
    private Map<String, String> reportes;
    private Map<String, String> qr;
    private Map<String, String> email;
    private Map<String, String> fileman;
    private Map<String, String> empresa;
    private Map<String, String> extra;
}
