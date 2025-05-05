package ar.com.acme.base.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase debe ser utilizada como un punto focal para el acceso a las propiedades configurables para
 * el sistema, de manera de tener encasulada, en una sola clase, todas las cuestiones relativas a estos.
 *
 * @author jmfragueiro
 * @version 20250421
 */
@Component
@ConfigurationProperties(prefix = "base")
@Getter
@Setter
public class BaseProperties {
    private Map<String, String> regexp;
    private Map<String, String> jws;
}
