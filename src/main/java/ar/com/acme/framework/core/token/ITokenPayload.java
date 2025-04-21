package ar.gov.posadas.mbe.framework.core.token;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import ar.gov.posadas.mbe.framework.common.Constantes;

import java.util.Collection;

/**
 * Un ITOKEN es un elemento que permite mantener "registrado" otro objeto cualquiera denominado
 * PAYLOAD o carga útil. Esta interface permite establecer el comportamiento de una clase para
 * esa carga útil registrada por el itoken.
 *
 * @author jmfragueiro
 * @version 20230601
 */
public interface ITokenPayload {
    Long getId();

    String getName();

    String getCredential();

    Collection<? extends ITokenAuthority> reinitAuthorities();

    Collection<? extends ITokenAuthority> getAuthorities();

    boolean isNonExpired();

    boolean isNonLocked();

    boolean isEnabled();

    default void verifyCanOperate() {
        if (!isNonLocked()) {
            throw new LockedException(Constantes.MSJ_SEC_ERR_USERLOCKED);
        }
        if (!isEnabled()) {
            throw new DisabledException(Constantes.MSJ_SEC_ERR_USERNOTENABLED);
        }
        ////////////////////////////////////////////////////////////////////////////
        // Luis 25/6/21
        // Si esta Expirado, le deja operar ya que el front le obligara a cambiar la
        // password
        // Expired opera sobre password vencido unicamente
        ////////////////////////////////////////////////////////////////////////////
        // if (!isNonExpired()) {
        //     throw new AuthException(C.MSJ_SEC_ERR_USEREXPIRED);
        // }
    }
}
