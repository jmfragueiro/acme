package ar.com.acme.application.email;

/**
 * <p>Esta interfase define el comportamiento deseado de un servicio que permita
 * validar el formato de una dirección de correo electrónica. Se espera que el
 * formato a validar pueda ser definido dinámicamente vía una expresión regular
 * y desde una propiedad modificable de manera externa al sistema.</p>
 *
 * @author jmfragueiro
 * @version 20250505
 */
public interface IEmailServiceValidator {
    Boolean isValidEmail(String email);
}
