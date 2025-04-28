package ar.com.acme.base.exception;

import ar.com.acme.base.common.BaseConstants;

public class ItemNotFoundException extends MessageException {
    public ItemNotFoundException(String item) {
        super(BaseConstants.MSJ_REP_ERR_NOITEM
                .concat(BaseConstants.SYS_CAD_SPACE)
                .concat(BaseConstants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(BaseConstants.SYS_CAD_CLOSETPE));
    }
}
