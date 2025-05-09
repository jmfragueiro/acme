package ar.com.acme.application.password;

/**
 * <p>Esta interfase define el comportamiento deseado de un servicio que permita
 * validar el formato de una contraseña de usuario (password). Se espera que el
 * formato a validar pueda ser definido dinámicamente vía una expresión regular.</p>
 *
 * @author jmfragueiro
 * @version 20250505
 */
public interface IPasswordService {
    Boolean isValidPassword(String rawPassword);

    String encode(CharSequence rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
