package ar.gov.posadas.mbe.framework.common;

/**
 * Este tipo registro debe ser utilizado generar un esquema común de respuesta para
 * cualqueir proceso que deba devoler el estado de una operación que pueda ser de
 * error o de suceso.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public record Response (boolean success, String mensaje) {
    public static Response ok() {
        return ok( Constantes.SYS_CAD_SUCCESS);
    }

    public static Response ok(String mensaje) {
        return new Response(true, Constantes.SYS_CAD_SUCCESS);
    }

    public static Response fail() {
        return fail(Constantes.SYS_CAD_FAIL);
    }

    public static Response fail(String mensaje) {
        return new Response(false, Constantes.SYS_CAD_FAIL);
    }
}
