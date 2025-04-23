package ar.com.acme.bootstrap.core.http;

/**
 * Este tipo registro debe ser utilizado generar un formato común de respuesta HTTP
 * ante errores o mensajes que no sean propios de una Entidad específica solicitada.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public record HttpResponseError(String mensaje) {
	public static HttpResponseError of(String mensaje) {
		return new HttpResponseError(mensaje);
	}
}
