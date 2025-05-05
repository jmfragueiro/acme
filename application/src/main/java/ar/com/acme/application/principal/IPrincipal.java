package ar.com.acme.application.principal;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Un TOKENPAYLOAD es un elemento que puede ser "registrado" dentro de otro objeto denominado
 * ITOKEN, representando su carga útil . Esta interface permite establecer el comportamiento de
 * una clase para esa carga útil registrada.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public interface IPrincipal {
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

    void setToken(UUID token);

    UUID getToken();

    void setLastLogin(LocalDateTime lastLogin);

    LocalDateTime getLastLogin();

    Object getCredentials();

    void verifyCanOperate();
}
