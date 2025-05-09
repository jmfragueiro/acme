package ar.com.acme.commons;

/**
 * Este tipo registro debe ser utilizado generar un formato común de respuesta HTTP
 * ante errores o mensajes que no sean propios de una Entidad específica solicitada.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public record ResponseError(String mensaje) {
	public static ResponseError of(String mensaje) {
		return new ResponseError(mensaje);
	}
}
