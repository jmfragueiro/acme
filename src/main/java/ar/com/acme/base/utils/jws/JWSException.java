package ar.com.acme.base.utils.jws;

import ar.com.acme.base.common.BaseConstants;
import ar.com.acme.base.exception.MessageException;

public class JWSException extends MessageException {
    public JWSException(String item) {
        super(BaseConstants.MSJ_REP_ERR_NOITEM
                .concat(BaseConstants.SYS_CAD_SPACE)
                .concat(BaseConstants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(BaseConstants.SYS_CAD_CLOSETPE));
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
