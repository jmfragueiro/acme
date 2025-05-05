package ar.com.acme.application.user;

import ar.com.acme.application.common.AppConstants;
import ar.com.acme.base.exception.MessageException;

public class UserException extends MessageException {
    public UserException(String message, String name) {
        super(message.concat(AppConstants.ADD_FORMATTER_STRING).formatted(name));
    }
}
