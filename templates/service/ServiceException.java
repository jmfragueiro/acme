package ar.com.acme.application.templates.service;

import ar.com.acme.commons.MessageException;

public class ServiceException extends MessageException {
    public ServiceException(String mensaje) {
        super(mensaje);
    }

    public ServiceException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
