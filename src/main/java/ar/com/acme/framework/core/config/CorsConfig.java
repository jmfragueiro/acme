package ar.com.acme.framework.core.config;

import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Propiedades;

import static java.util.Collections.singletonList;

@Component
public class CorsConfig {
    private final String corsAllowOrigin;
    private final String corsAllowMethod;
    private final String corsAllowHeader;

    public CorsConfig(Propiedades propiedades) {
        this.corsAllowOrigin = propiedades.getSecurity().get("cors_allow_origin");
        this.corsAllowMethod = propiedades.getSecurity().get("cors_allow_method");
        this.corsAllowHeader = propiedades.getSecurity().get("cors_allow_header");
    }

    protected CorsConfigurationSource corsConfigurationSource() {
        // setea la configuración de seguridad de CORS
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(singletonList(corsAllowOrigin));
        configuration.setAllowedMethods(Arrays.stream(corsAllowMethod.split(",")).toList());
        configuration.setAllowedHeaders(Arrays.stream(corsAllowHeader.split(",")).toList());
        configuration.setAllowCredentials(true);

        // define para qué URLs aplicar esa configuración
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(Constantes.SYS_CAD_URLALL, configuration);

        return source;
    }
}
