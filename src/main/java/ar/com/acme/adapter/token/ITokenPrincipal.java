package ar.com.acme.adapter.token;

import org.springframework.security.authentication.DisabledException;
import ar.com.acme.bootstrap.common.Constants;

import java.util.Collection;

/**
 * Un TOKENPAYLOAD es un elemento que puede ser "registrado" dentro de otro objeto denominado
 * ITOKEN, representando su carga útil . Esta interface permite establecer el comportamiento de
 * una clase para esa carga útil registrada.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public interface ITokenPrincipal {
    ///////////////////////////////////////////////////////
    // ESTO ES PARA GENERAR USUARIOS Y CLAVES Y PROBAR:  //
    // (hay que debuggear y parar en la captura de pass  //
    //  y guardar el pass generado en la base de datos)  //
    ///////////////////////////////////////////////////////
    // @Autowired                                        //
    // private BCryptPasswordEncoder passEncoder;        //
    //                                                   //
    // Usuario us = new Usuario();                       //
    // us.setUsername("jmfragueiro");                    //
    // us.setPassword(passEncoder.encode("fito"));       //
    // String pass = us.getPassword();                   //
    ///////////////////////////////////////////////////////
    String getName();

    void setToken(String token);

    String getToken();

    String getCredential();

    Collection<? extends ITokenAuthority> getAuthorities();

    boolean isEnabled();

    default void verifyCanOperate() {
        if (!isEnabled()) {
            throw new DisabledException(Constants.MSJ_USR_ERR_USERNOTENABLED);
        }
    }
}
