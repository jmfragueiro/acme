package ar.com.acme.commons;

import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Esta clase debe ser utilizada como un punto focal para todos los metodos utilitarios genéricos
 * del sistema, de manera de tener encapsulada, en una sola clase, todas estas utilidades generales.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Tools {
    /**
     * Este metodo retorna el lugar exacto desde donde es llamado como una cadena presentada como:
     * [CLASE:METODO:NroLinea]. Hay que tener en cuenta que el número de linea puede estar asociado
     * (en una llamada desde una excepcion) a la línea desde donde se gestiona la exepcion (catch).
     *
     * @param index, el indice de metodo llamante requerido.
     * @return retorna el lugar exacto desde donde es llamado.
     */
    public static String getNombreMetodoLlamante(int index) {
        StackTraceElement ste = new Exception().getStackTrace()[index];
        return Constants.SYS_CAD_OPENTYPE.concat(ste.getClassName())
                                         .concat(Constants.SYS_CAD_LOGSEP)
                                         .concat(ste.getMethodName())
                                         .concat(Constants.SYS_CAD_LOGSEP)
                                         .concat(String.valueOf(ste.getLineNumber()))
                                         .concat(Constants.SYS_CAD_CLOSETPE);
    }

    /**
     * Este metodo permite obtener un String con un error mas o menos formateado.
     *
     * @param error    el error principal
     * @param extra    una cadena extra para aclarar el error
     * @param username un nombre de usuario por si hiciera falta
     * @return la cadena de error formateada
     */
    public static String getCadenaErrorFormateada(String error, String extra, String username) {
        return ((error != null && !error.isBlank())
                ? error.concat(Constants.SYS_CAD_LOGSEP)
                : Constants.SYS_CAD_ERROR).concat(Constants.SYS_CAD_SPACE)
                                          .concat((extra != null && !extra.isBlank())? extra : Constants.SYS_CAD_NULL)
                                          .concat(Constants.SYS_CAD_SPACE)
                                          .concat((username != null && !username.isBlank())
                                                ? Constants.SYS_CAD_OPENTYPE.concat(username).concat(Constants.SYS_CAD_CLOSETPE)
                                                : Constants.SYS_CAD_NULL)
                                          .trim();
    }

    /**
     * Este metodo retorna un hash con md5 de un string recibido.
     *
     * @param texto el texto para el cual calcular el hash
     * @return el hash obtenido
     */
    public static String getSha256Token(String texto) {
        String sb;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            sb = DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (Exception exc) {
            sb = Constants.SYS_CAD_NULL;
        }

        return sb;
    }

    /**
     * Este metodo permite obtener un String vacio ('') si el String
     * pasado como parametro es nulo, si no, devuelve el String parametro.
     *
     * @param objeto
     * @return un objeto para controlar si es nulo
     */
    public static String getEmptyStringOnNull(String objeto) {
        return ((objeto == null) ? Constants.SYS_CAD_NULL : objeto);
    }
}
