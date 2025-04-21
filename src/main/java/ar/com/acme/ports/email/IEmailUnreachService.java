package ar.gov.posadas.mbe.ports.email;

public interface IEmailUnreachService {
    boolean existByTipoContacto(Long tipo, String contacto);

    void addByTipoContacto(Long tipo, String contacto, String observacion);
}
