package ar.com.acme.bootstrap.http;

import org.springframework.http.HttpStatus;

/**
 * Este tipo registro debe ser utilizado generar un formato común de respuesta HTTP
 * ante errores o mensajes que no sean propios de una Entidad específica solicitada.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public record HttpResponseBody(String timestamp,
							   HttpStatus status,
							   String mensaje,
							   Object object) { }
