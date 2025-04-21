package ar.gov.posadas.mbe.ports.email;

import ar.gov.posadas.mbe.framework.common.Response;

public interface IEmailReadNotificacionClient {
    Response registrarVisto(String tokenLeido);
}
