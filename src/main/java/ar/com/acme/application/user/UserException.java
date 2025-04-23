package ar.com.acme.application.user;

import ar.com.acme.adapter.exception.MessageException;
import ar.com.acme.application.common.AppConstants;

public class UserException extends MessageException {
    public UserException(String name, String message) {
        super(message.concat(AppConstants.ADD_FORMATTER_STRING).formatted(name));
    }
}
