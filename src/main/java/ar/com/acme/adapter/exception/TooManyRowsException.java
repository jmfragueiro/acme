package ar.com.acme.adapter.exception;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.bootstrap.common.Constants;

public class TooManyRowsException extends MessageException {
    public TooManyRowsException(String entidad, String item) {
        super(AdapterConstants.MSJ_REP_ERR_TOOMANY
                .concat(Constants.SYS_CAD_SPACE)
                .concat(Constants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constants.SYS_CAD_CLOSETPE));
    }
}
