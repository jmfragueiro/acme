package ar.gov.posadas.mbe.framework.core.exception;

/**
 * Esta clase de excepcion es la base de la jerarquia de excepciones del framework que PUEDEN
 * NO SER CAPTURADAS dentro de la aplicación sino que se espera que lleguen al usuario con un
 * mensaje claro y que le permita entender una situacion determinada sobre la que actuar y se
 * usa para encapsular el comportamiento general que debe tener una excepcion dentro del mismo.
 * <p>
 * La clase maneja da la posibilidad de crear una excepcion con un mensaje generico, con uno
 * especifico, o bien con uno especifico mas un texto extra aclaratorio. Ademas, esta clase
 * permite logear una excepcion, como un mensaje de nivel ERROR para el caso de que el nivel
 * de la aplicacion lo permita, utilizando para ello metodos estaticos de la clase LogSistema.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public class UtilException extends MessageException {
    public UtilException(String mensaje) {
        super(mensaje);
    }

    public UtilException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
