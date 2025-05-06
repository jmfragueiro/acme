package ar.com.acme.bootstrap.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.acme.bootstrap.common.ResponseError;
import ar.com.acme.bootstrap.common.Constants;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Esta clase debe ser utilizada como un punto focal para la generación de mensajes
 * de error para rellenar las respuestas a peticiones HTTP, para tener encapsuladas,
 * en una sola clase, todas las cuestiones asociadas a este tipo de acciones.
 *
 * @author jmfragueiro
 * @version 20230601
 */
public final class HttpResponseService {
    /**
     * Este metodo permite generar una salida al objeto HttpServletResponse, que
     * representa la respuesta de una peticion HTTP, con un cuerpo de respuesta
     * en formato JSON para un error o excepción del sistema, en forma unificada.
     *
     * @param response  el objeto HttpServletResponse asociado a la respuesta
     * @param body   el cuerpo de la respuesta como un HttpResponse
     */
    public static void respondHandler(HttpServletResponse response, HttpResponseBody body) throws IOException {
        response.setContentType(Constants.SYS_CAD_APP_MIMETYPE_JSON);
        response.setStatus(body.status().value());

        new ObjectMapper().writeValue(response.getOutputStream(), getJsonResponse(body));
    }

    /**
     * Este metodo permite obtener una cadena, con el cuerpo de respuesta HTTP
     * en formato JSON para una respuesta http, en forma unificada.
     *
     * @param response  el cuerpo de la respuesta HTTP
     * @return un string JSON con el cuerpo de respuesta formateado
     */
    public static String getJsonResponse(HttpResponseBody response) throws IOException {
        return new ObjectMapper().writeValueAsString(ResponseError.of(response.mensaje()));
    }
}
