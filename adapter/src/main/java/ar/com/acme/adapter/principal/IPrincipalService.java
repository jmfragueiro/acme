package ar.com.acme.adapter.principal;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Esta interfase define el comportamiento deseado de un servicio que permita obtener y,
 * posteriormente, actualizar a los objetos {@link IPrincipal} con información asociada
 * a su utilización durante el procesamiento del requerimiento web actual.</p>
 * <p>Esta interfase representa, junto con {@link IPrincipal}, lo únicos puntos de relación
 * entre los módulos application y bootstrap.</p>
 *
 * @author jmfragueiro
 * @version 20250505
 * @see IPrincipal
 */
public interface IPrincipalService<U extends IPrincipal> {
    Optional<U> findByName(String name);

    Optional<U> findByToken(UUID token);

    Collection<String> getAuthorities(U principal);

    void updatePrincipal(U principal);
}
