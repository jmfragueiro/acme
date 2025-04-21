package ar.com.acme.framework.core.token;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import ar.com.acme.framework.common.Constantes;

import java.util.Collection;

/**
 * Un TOKENPAYLOAD es un elemento que puede ser "registrado" dentro de otro objeto denominado
 * ITOKEN, representando su carga útil . Esta interface permite establecer el comportamiento de
 * una clase para esa carga útil registrada.
 *
 * @author jmfragueiro
 * @version 20250421
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
            throw new LockedException(Constantes.MSJ_USR_ERR_USERLOCKED);
        }
        if (!isEnabled()) {
            throw new DisabledException(Constantes.MSJ_USR_ERR_USERNOTENABLED);
        }
    }
}
