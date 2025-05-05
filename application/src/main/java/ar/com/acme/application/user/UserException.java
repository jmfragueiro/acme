package ar.com.acme.application.user;

import ar.com.acme.application.common.Constants;
import ar.com.acme.application.common.MessageException;

public class UserException extends MessageException {
    public UserException(String message, String name) {
        super(message.concat(Constants.ADD_FORMATTER_STRING).formatted(name));
    }
}
