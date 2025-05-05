package ar.com.acme.application.phone;

import ar.com.acme.application.common.Constants;
import ar.com.acme.application.common.MessageException;

public class PhoneException extends MessageException {
    public PhoneException(String name, String message) {
        super(message.concat(Constants.ADD_FORMATTER_STRING).formatted(name));
    }
}
