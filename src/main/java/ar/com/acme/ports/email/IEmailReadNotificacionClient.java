package ar.com.acme.ports.email;

import ar.com.acme.framework.common.Response;

public interface IEmailReadNotificacionClient {
    Response registrarVisto(String tokenLeido);
}
