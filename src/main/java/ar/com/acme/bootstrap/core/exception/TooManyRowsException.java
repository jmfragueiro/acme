package ar.com.acme.bootstrap.core.exception;

import ar.com.acme.bootstrap.common.Constantes;

public class TooManyRowsException extends MessageException {
    public TooManyRowsException(String entidad, String item) {
        super(Constantes.MSJ_REP_ERR_TOOMANY
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(Constantes.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constantes.SYS_CAD_CLOSETPE));
    }
}
