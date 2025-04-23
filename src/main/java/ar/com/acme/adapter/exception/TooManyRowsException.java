package ar.com.acme.adapter.exception;

import ar.com.acme.adapter.common.Constantes;
import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.framework.exception.MessageException;

public class TooManyRowsException extends MessageException {
    public TooManyRowsException(String entidad, String item) {
        super(Constantes.MSJ_REP_ERR_TOOMANY
                .concat(Constants.SYS_CAD_SPACE)
                .concat(Constants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constants.SYS_CAD_CLOSETPE));
    }
}
