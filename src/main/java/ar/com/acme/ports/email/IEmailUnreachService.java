package ar.com.acme.ports.email;

public interface IEmailUnreachService {
    boolean existByTipoContacto(Long tipo, String contacto);

    void addByTipoContacto(Long tipo, String contacto, String observacion);
}
