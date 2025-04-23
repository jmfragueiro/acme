package ar.com.acme.adapter.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Esta clase debe ser utilizada como un punto focal para el acceso a las propiedades configurables para
 * el sistema, de manera de tener encasulada, en una sola clase, todas las cuestiones relativas a estos.
 *
 * @author jmfragueiro
 * @version 20250421
 */
@Component
@ConfigurationProperties(prefix = "framework")
@Getter
public class Properties {
    private Map<String, String> security;
    private Map<String, String> token;
}
