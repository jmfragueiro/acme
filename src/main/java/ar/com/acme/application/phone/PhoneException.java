package ar.com.acme.application.phone;

import ar.com.acme.adapter.exception.MessageException;
import ar.com.acme.application.common.AppConstants;

public class PhoneException extends MessageException {
    public PhoneException(String name, String message) {
        super(message.concat(AppConstants.ADD_FORMATTER_STRING).formatted(name));
    }
}
