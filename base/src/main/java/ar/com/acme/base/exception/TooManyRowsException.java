package ar.com.acme.base.exception;

import ar.com.acme.base.common.BaseConstants;

public class TooManyRowsException extends MessageException {
    public TooManyRowsException(String entidad, String item) {
        super(BaseConstants.MSJ_REP_ERR_TOOMANY
                .concat(BaseConstants.SYS_CAD_SPACE)
                .concat(BaseConstants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(BaseConstants.SYS_CAD_CLOSETPE));
    }
}
