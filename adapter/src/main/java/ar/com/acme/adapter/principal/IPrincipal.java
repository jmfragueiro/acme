package ar.com.acme.adapter.principal;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>Esta interfase define el comportamiento deseado de un objeto que representa al usuario
 * actualmente conectado y de cara al al contexto de seguridad que se aplique dentro del
 * sistema, para que pueda ser recuperado para validaciones, controles de acceso, etc. Se
 * espera que sea un objeto que solo tenga vida durante el procesamiento del requerimiento
 * web actual. Actúa además, junto con su servicio, como unico punto de relación entre la
 * módulo de aplicación y el de bootstrap, permitiendo evitar el acoplamiento etre estos
 * módulos.</p>
 * <p>Se espera que un objeto Principal se asocie internamente a la entidad, dentro de la
 * aplicación específica, que representa a un usuario, la que entonces será desconocida
 * para el mecanismo de contexto de seguridad aplicado por el framework utilizado (el cual
 * sera represenatado por el módulo bootstrap).</p>
 *
 * @author jmfragueiro
 * @version 20250505
 * @see IPrincipalService
 */
public interface IPrincipal {
    String getName();

    void setToken(UUID token);

    UUID getToken();

    void setLastLogin(LocalDateTime lastLogin);

    LocalDateTime getLastLogin();

    boolean canOperate();

    boolean matchCredential(String rawCredential);
}
