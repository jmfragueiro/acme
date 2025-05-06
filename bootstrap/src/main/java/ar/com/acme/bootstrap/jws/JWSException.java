package ar.com.acme.bootstrap.jws;

import ar.com.acme.commons.Constants;
import ar.com.acme.commons.MessageException;

public class JWSException extends MessageException {
    public JWSException(String item) {
        super(Constants.MSJ_TOK_ERR_GENERAL
                .concat(Constants.SYS_CAD_SPACE)
                .concat(Constants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constants.SYS_CAD_CLOSETPE));
    }

    /**
     * Esta version del contructor permite crear una excepcion con mensaje y con datos extras.
     *
     * @param mensaje El mensaje que describe la excepcion.
     * @param extra   La cadena con datos extras para mostrarInnerLayout en la exepcion
     */
    public JWSException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
