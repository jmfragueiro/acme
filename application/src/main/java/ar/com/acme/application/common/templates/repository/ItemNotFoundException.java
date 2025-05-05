package ar.com.acme.application.common.templates.repository;

import ar.com.acme.application.common.Constants;
import ar.com.acme.application.common.MessageException;

public class ItemNotFoundException extends MessageException {
    public ItemNotFoundException(String item) {
        super(Constants.MSJ_REP_ERR_NOITEM
                .concat(Constants.SYS_CAD_SPACE)
                .concat(Constants.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constants.SYS_CAD_CLOSETPE));
    }
}
