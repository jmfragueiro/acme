package ar.gov.posadas.mbe.framework.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Esta clase debe ser utilizada como un punto focal para todos los metodos genericos de herramienta dentro
 * del framework, de manera de tener encapsulada, en una sola clase, todas las cuestiones asociadas a este
 * tipo de necesidades.
 *
 * @author jmfragueiro
 * @version 20200201
 */
@Service
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
        return Constantes.SYS_CAD_OPENTYPE.concat(ste.getClassName())
                .concat(Constantes.SYS_CAD_LOGSEP)
                .concat(ste.getMethodName())
                .concat(Constantes.SYS_CAD_LOGSEP)
                .concat(String.valueOf(ste.getLineNumber()))
                .concat(Constantes.SYS_CAD_CLOSETPE);
    }

    /**
     * Este metodo permite determinar si una lista contiene al menos un item (el primero) de una
     * clase determinada, pasada como argumento. Para esto verifica la clase del primer elemento
     * de la lista.
     *
     * @param lista, la lista sobre la cua quiere verificarse la clase.
     * @param clase, la clase contra la cual veririfcar la lista.
     * @return retorna verdadero si el primer elemento de la lista es de la clase a verificar de lo contrario retorna false.
     */
    public static boolean isListInstanceOf(List<?> lista, Class<?> clase) {
        return lista != null && lista.size() > 0 && lista.get(0).getClass().equals(clase);
    }

    /**
     * Este metodo permite determinar si una Enumeracion contiene un item con el nombre igual al
     * pasado como argumento. Para determinar contra que clase de Enumeracion comprobar, basta
     * con pasar una constante de dicha enumeracion como 'template'.
     *
     * @param template, una constante de la enumeracion sobre la cual se quiere verificar la cadena de nombre.
     * @param nombre,   la cadena contra la cual veririfcar la enumeracion.
     * @return retorna verdadero si la enumeracion contiene un valor con el nombre pasado, de lo contrario retorna false.
     */
    public static boolean enumContainsString(Enum<?> template, String nombre) {
        return getEnumConstantFromString(template, nombre) != null;
    }

    /**
     * Este metodo permite determinar si una Enumeracion contiene un item con el nombre igual al
     * pasado como argumento y, en tal caso, retornar la contsante de la enum correspondiente al
     * nombre pasado. Para determinar contra que clase de Enumeracion comprobar, basta con pasar
     * una constante de dicha enumeracion como 'template'.
     *
     * @param template, una constante de la enumeracion sobre la cual se quiere verificar la cadena de nombre.
     * @param nombre,   la cadena contra la cual veririfcar la enumeracion.
     * @return retorna la constante respectiva si la enumeracion contiene un valor con el nombre pasado, de lo contrario retorna null.
     */
    public static <E extends Enum<E>> E getEnumConstantFromString(Enum<E> template, String nombre) {
        for (E c : template.getDeclaringClass().getEnumConstants()) {
            if (c.toString().equals(nombre)) {
                return c;
            }
        }

        return null;
    }

    /**
     * Este metodo permite generar un mensaje tipo excepción con el template:
     * "CADENA DE MENSAJE [{OBJETO SERIALIZADO EN FORMATO JSON}]"
     * para que pueda ser registrado en alguno de las interfaces de salida del sistema.
     *
     * @param mensaje la cadena del mensaje a formatear
     * @param objeto  el objeto a serializar tipo JSON para agregar al mensaje a formatear
     * @return el mensaje con el formato "de excepción" comentado
     * @throws JsonProcessingException
     */
    public static String getExcMesgWithJSONObject(String mensaje, Object objeto) throws JsonProcessingException {
        return getExcFormatedMesg(mensaje, getObjetoSerializadoAStringJSON(objeto));
    }

    /**
     * Este metodo permite generar un mensaje tipo excepción con el template:
     * "CADENA DE MENSAJE [DATOS ADJUNTOS]"
     * para que pueda ser registrado en alguno de las interfaces de salida del sistema.
     *
     * @param mensaje la cadena del mensaje a formatear
     * @param adjunto una cadena de datos adjuntos para agregar al mensaje a formatear
     * @return el mensaje con el formato "de excepción" comentado
     */
    public static String getExcFormatedMesg(String mensaje, String adjunto) {
        return mensaje.concat(Constantes.SYS_CAD_SPACE).concat(Constantes.SYS_CAD_OPENTYPE).concat(adjunto).concat(Constantes.SYS_CAD_CLOSETPE);
    }

    /**
     * Este metodo permite transformar un objeto cualquiera a una cadena con formato JSON.
     *
     * @param objeto el objeto a serializar al formato JSON
     * @return la cadena con formato JSON respectiva al objeto pasado como argumento
     * @throws JsonProcessingException
     */
    public static String getObjetoSerializadoAStringJSON(Object objeto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(((objeto != null) ? objeto : Constantes.SYS_CAD_NULL));
    }

    /**
     * Este metodo permite obtener un nombre de clase formateado para ser mostrado en el sistema, y
     * con la posibilidad de que el mismo sea filtrado removiendo cierta parte del nombre de clase
     * para que quede solamente un componente del nombre.
     *
     * @return el nombre obtenido desde el formateo
     */
    public static String getNombreClaseFormateado(Class<?> clase, String remover) {
        return clase.getSimpleName().toUpperCase().replace(remover.toUpperCase(), "");
    }

    /**
     * Este metodo permite obtener un mensaje de excepcion mas claro cuando se trata de una
     * excepcion originada en un problema de SQL. En caso de no ser una excepción originada
     * en un problema de SQL, devuelve solamente el mensaje de la excepcion.
     *
     * @param ex
     * @return un mensaje que puede tener datos extras si es una excepción originada en un problema SQL
     */
    public static String getMesgSQLException(Exception ex) {
        return (ex.getCause() != null && ex.getCause() instanceof JDBCException)
                ? Tools.getExcFormatedMesg(ex.getMessage(), ((JDBCException) ex.getCause()).getSQLException().getMessage())
                : ex.getMessage();
    }

    /**
     * Este metodo quitas espacios en blanco de un string
     *
     * @param texto
     * @return texto limpio de caracteres no deseados
     */
    public static String getquitarBlancos(String texto) {

        return texto.replace(" ", "");
    }

    /**
     * Este metodo permite limpiar un string recibido, quitando caracteres no deseados
     *
     * @param texto
     * @return texto limpio de caracteres no deseados
     */
    public static String getlimpiarString(String texto) {

        if (texto != null && !texto.trim().isEmpty()) {

            /**
             * Caracteres Raros
             */
            // Chr 126 = ~
            // Chr 124 = |
            // Chr  44 = ,
            // Chr  59 = ;
            // Chr  34 = Comillas
            // Chr  39 = Apostrofe
            // Chr  96 = Apostrofe
            // Chr 239 = Apostrofe

            char[] raros = new char[]
                    {
                            126,
                            124,
                            44,
                            59,
                            34,
                            39,
                            96,
                            239
                    };

            for (int i = 0; i > raros.length; i++)
                texto = texto.replace(raros[i], (char) 0);

            /**
             * Combinaciones Especificas
             */
            texto = texto.replace(".-", "");
        }

        return texto;
    }

    /**
     * Este metodo permite limpiar un string recibido, dejando solo letras y numeros
     *
     * @param texto
     * @return texto limpio segun se requiere
     */
    public static String getStringSoloLetrasYNumeros(String texto) {

        String textoNuevo = "";

        if (texto != null && !texto.trim().isEmpty()) {
            for (int x = 0; x < texto.length(); x++) {
                char c = texto.charAt(x);
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                    textoNuevo = textoNuevo + c;
                }
            }
        }

        return textoNuevo;
    }

    /**
     * Este metodo permite limpiar un string recibido, dejando solo letras
     *
     * @param texto
     * @return texto limpio segun se requiere
     */
    public static String getStringSoloLetras(String texto) {

        String textoNuevo = "";

        if (texto != null && !texto.trim().isEmpty()) {
            for (int x = 0; x < texto.length(); x++) {
                char c = texto.charAt(x);
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    textoNuevo = textoNuevo + c;
                }
            }
        }

        return textoNuevo;
    }

    /**
     * Este metodo permite limpiar un string recibido, dejando solo numeros
     *
     * @param texto
     * @return texto limpio segun se requiere
     */
    public static String getStringSoloNumeros(String texto) {

        String textoNuevo = "";

        if (texto != null && !texto.trim().isEmpty()) {
            for (int x = 0; x < texto.length(); x++) {
                char c = texto.charAt(x);
                if (c >= '0' && c <= '9') {
                    textoNuevo = textoNuevo + c;
                }
            }
        }

        return textoNuevo;
    }

    /**
     * Este metodo permite obtener un String vacio ('') si el String
     * pasado como parametro es nulo, si no, devuelve el String parametro.
     *
     * @param objeto
     * @return un objeto para controlar si es nulo
     */
    public static String getEmptyStringOnNull(String objeto) {
        return ((objeto == null) ? Constantes.SYS_CAD_NULL : objeto);
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
        return (Strings.isNotEmpty(error) ? error.concat(Constantes.SYS_CAD_LOGSEP) : Constantes.SYS_APP_TXTERROR)
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(Strings.isNotEmpty(extra) ? extra : Constantes.SYS_CAD_NULL)
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(Constantes.SYS_CAD_OPENTYPE)
                .concat(Strings.isNotEmpty(username) ? username : Constantes.SYS_CAD_NULL)
                .concat(Constantes.SYS_CAD_CLOSETPE);
    }

    /**
     * Este metodo retorna un hash con md5 de un string recibido.
	 *
     * @param texto el texto para el cual calcular el hash
     * @return el hash obtenido
     */
    public static String getMd5Token(String texto) {
        final StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(texto.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest);
            sb.append(myHash);
        } catch (Exception exc) {
            sb.append("ImposibleObtener");
        }

        return sb.toString();
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
            sb = Constantes.SYS_CAD_NULL;
        }

        return sb;
    }

    /**
     * Este metodo una cadena de Tipo MIME para un nombre de archivo dado, del cual estableece su tipo concreto.
     *
     * @param filename elnombre del archivo para el cual se desea obtener la cadena de tipo MIME
     * @return la cadena de tipo MIME corrspondiente al tipo de archivo nombrado
     */
    public static String obtenerTipoMimeArchivo(String filename) {
        String tipo = null;
        String extension = obtenerExtensionArchivo(filename);
        if (extension.equals("doc")) {
            tipo = "application/vnd.ms-word";
        }
        if (extension.equals("docx")) {
            tipo = "application/vnd.ms-word";
        }
        if (extension.equals("xls")) {
            tipo = "application/vnd.ms-excel";
        }
        if (extension.equals("xlsx")) {
            tipo = "application/vnd.ms-excel";
        }
        if (extension.equals("pdf")) {
            tipo = "application/pdf";
        }
        if (extension.equals("png")) {
            tipo = "image/png";
        }
        if (extension.equals("jpg")) {
            tipo = "image/jpeg";
        }
        if (extension.equals("jpeg")) {
            tipo = "image/jpeg";
        }
        if (extension.equals("rtf")) {
            tipo = "application/rtf";
        }
        if (extension.equals("csv")) {
            tipo = "text/csv";
        }
        if (extension.equals("txt")) {
            tipo = "text/plain";
        }
        if (extension.equals("ppt")) {
            tipo = "application/vnd.ms-powerpoint";
        }

        return tipo;
    }

    /**
     * Este metodo extrae y retorna la extensión de un nombre de archivo.
     *
     * @param filename el nombre del archivo para el cual se desea extraer la extensión
     * @return la extensión obtenida desde el nombre de archivo
     */
    public static String obtenerExtensionArchivo(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    public static Boolean validarCuit(String cuit) {
        //Elimino todos los caracteres que no son números
        cuit = cuit.replaceAll("[^\\d]", "");

        //Controlo si son 11 números los que quedaron, si no es el caso, ya devuelve falso
        if (cuit.length() != 11) {
            return false;
        }

        //Convierto la cadena que quedó en una matriz de caracteres
        String[] cuitArray = cuit.split("");

        //Inicializo una matriz por la cual se multiplicarán cada uno de los dígitos
        Integer[] serie = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

        //Creo una variable auxiliar donde guardo los resultados del calculo del número validador
        Integer aux = 0;

        //Recorro las matrices de forma simultanea, sumando los productos de la serie por el número en la misma posición
        for (int i = 0; i < 10; i++) {
            aux += Integer.parseInt(cuitArray[i]) * serie[i];
        }

        //Hago como se especifica: 11 menos el resto de la división de la suma de productos anterior por 11
        aux = 11 - (aux % 11);

        //Si el resultado anterior es 11 el código es 0
        if (aux == 11) {
            aux = 0;
            //o si el resultado anterior es 10 el código es 9
        } else if (aux == 10) {
            aux = 9;
        }

        //Devuelve verdadero si son iguales, falso si no lo son
        //(Esta forma esta dada para prevenir errores, se puede usar: return Integer.valueOf(cuitArray[11]) == aux;)
        return Objects.equals(Integer.valueOf(cuitArray[10]), aux);
    }

    public static String cadenaAleatoria(int longitud) {
        // El banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    public static String rellenarCerosIzquierda(long valor, int longitud) {
        return String.format("%0" + longitud + "d", valor);
    }
}
