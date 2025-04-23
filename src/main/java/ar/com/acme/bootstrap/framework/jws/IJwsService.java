package ar.com.acme.bootstrap.framework.jws;

import ar.com.acme.adapter.token.IEntityToken;

/**
 * Esta interface implementa el comportamiento necesario aquí para el servicio
 * de generación y operación de elementos JWS (JSON Web Signature), los cuales
 * se utilizan aquí para el intercambio de información de sesiones activas, con
 * posibilidad de agregar datos "extras" a las mismas (propios de la aplicación
 * que utilice este "proto-framework"). La implementación efectiva aquí permite
 * flexibilidad al soportar la inyección "parametrizada" de la clase de servicio
 * para el dato extra a utilizarse (es decir permitiendo definir la clase concreta
 * via el application.properties en la propiedad: extradata.service).
 *
 * @author jmfragueiro
 * @version 20230601
 */
public interface IJwsService {
    String generateJws(IEntityToken source);

    void validateJws(String jws);

    String getIdFromJws(String jws);

    String getNameFromJws(String jws);
}
