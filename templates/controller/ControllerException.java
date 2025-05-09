package ar.com.acme.application.templates.controller;

import ar.com.acme.commons.MessageException;

public class ControllerException extends MessageException {
    public ControllerException(String mensaje) {
        super(mensaje);
    }

    public ControllerException(String mensaje, String extra) {
        super(mensaje, extra);
    }
}
