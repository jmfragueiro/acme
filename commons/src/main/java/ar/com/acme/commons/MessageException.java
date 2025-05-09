package ar.com.acme.commons;

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
 * @version 20250505
 */
public class MessageException extends RuntimeException {
    public MessageException() {
        this(Constants.MSJ_ERR_EXCEPCION, null);
    }

    public MessageException(String mensaje) {
        this(mensaje, null);
    }

    public MessageException(String mensaje, String extra) {
        super(mensaje);
        registrarMensaje(mensaje, extra);
    }

    private void registrarMensaje(String mensaje, String extra) {
        Logging.error(this.getClass(), Tools.getCadenaErrorFormateada(mensaje, extra, null));
    }
}
