package ar.com.acme.bootstrap.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.acme.commons.ResponseError;
import ar.com.acme.commons.Constants;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Esta clase debe ser utilizada como un punto focal para la generación de mensajes
 * de error para rellenar las respuestas a peticiones HTTP, para tener encapsuladas,
 * en una sola clase, todas las cuestiones asociadas a este tipo de acciones.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public final class HttpResponseService {
    public static void respondHandler(HttpServletResponse response, HttpResponseBody body) throws IOException {
        response.setContentType(Constants.SYS_CAD_APP_MIMETYPE_JSON);
        response.setStatus(body.status().value());

        new ObjectMapper().writeValue(response.getOutputStream(), getJsonResponse(body));
    }

    public static String getJsonResponse(HttpResponseBody response) throws IOException {
        return new ObjectMapper().writeValueAsString(ResponseError.of(response.mensaje()));
    }
}
