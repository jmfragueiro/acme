package ar.com.acme.bootstrap.core.jws;

import java.util.List;

/**
 * Esta interface establece el comportamiento necesario para cualquier objeto que pueda
 * (a los fines del presente "proto-famework") ayudar a generar un Dato JWS (JSON Web
 * Signature) generado y operado por el servicio JwsService.
 *
 * @author jmfragueiro
 * @version 20230601
 */
public interface IJwsDataHelper {
    Object getId();

    String getSubject();

    List<String> authClaim();
}
