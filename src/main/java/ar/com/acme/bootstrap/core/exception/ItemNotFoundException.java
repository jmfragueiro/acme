package ar.com.acme.bootstrap.core.exception;

import ar.com.acme.bootstrap.common.Constantes;

public class ItemNotFoundException extends MessageException {
    public ItemNotFoundException(String item) {
        super(Constantes.MSJ_REP_ERR_NOITEM
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(Constantes.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constantes.SYS_CAD_CLOSETPE));
    }
}
