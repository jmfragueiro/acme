package ar.com.acme.base.common;

public class BaseConstants {
    //  **********************************************************
    //  * CADENAS DE MENSAJES DEL PROCESAMIENTO DE PERSISTENCIA
    //  **********************************************************
    public static final String MSJ_REP_ERR_ATSAVEDATA = "ERROR AL INTENTAR PERSISTIR UN DATO";
    public static final String MSJ_REP_ERR_FIELD_EMPTY = "DEBEN CARGARSE DATOS PARA EL CAMPO OBLIGATORIO: ";
    public static final String MSJ_REP_ERR_FIELD_NOK = "DEBEN CARGARSE DATOS VALIDOS PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_FIELD_LONG_NOK = "LA LONGITUD DE DATOS INGRESADOS ES INVALIDA PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_NOITEM = "NO PUDO OBTENERSE EL ELEMENTO DESEADO!";
    public static final String MSJ_REP_ERR_TOOMANY = "SE OBTUVIERON MAS ELEMENTOS QUE LOS DESEADOS!";
    public static final String MSJ_REP_ERR_NULLITEM = "NO SE HA INFORMADO EL ELEMENTO DESEADO!";

    //  **********************************************************
    //  * CADENAS DE DE TEXTOS COMUNES
    //  **********************************************************
    public static final String SYS_CAD_OPENTYPE = "[";
    public static final String SYS_CAD_CLOSETPE = "]";
    public static final String SYS_CAD_ENTITY_NEW = "NUEVO";
    public static final String SYS_CAD_REFER = "->";
    public static final String SYS_CAD_SPACE = " ";
    public static final String SYS_CAD_LOGSEP = ":";
    public static final String SYS_CAD_NULL = "";
    public static final String SYS_CAD_ERROR = "ERROR:";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE ERROR COMUNES
    //  **********************************************************
    public static final String MSJ_ERR_EXCEPCION = "ERROR INTERNO DEL SISTEMA";

    //  **********************************************************
    //  * CADENAS ASOCIADAS AL MANEJO DE TOKENS
    //  **********************************************************
    public static final String MSJ_TOK_ERR_BADJWTSIGN = "FIRMA DE TOKEN INVALIDA";
    public static final String MSJ_TOK_ERR_BADJWT = "DATOS DE TOKEN INCORRRECTOS";
    public static final String MSJ_TOK_ERR_TOKENNOTSUP = "TOKEN NO SOPORTADO POR LA PLATAFORMA";
    public static final String MSJ_TOK_ERR_EMPTYCLAIM = "CADENA DE CONTENIDO DE TOKEN VACIA";
    public static final String MSJ_TOK_ERR_CANTEXTRACTTED = "NO SE OBTUVO NINGUN VALOR EXTRA DESDE EL TOKEN";
    public static final String MSJ_TOK_ERR_BADTOKEN = "FORMATO DE TOKEN INVALIDO";
    public static final String MSJ_TOK_ERR_TOKENUSERNOOP = "EL ESTADO DEL USUARIO NO PERMITE GENERAR UN TOKEN VALIDO";
    public static final String MSJ_TOK_ERR_TOKENREINIT = "ERROR AL INTENTAR REVALIDAR UN TOKEN";
    public static final String MSJ_TOK_ERR_NOAUTHCAD = "SE REQUIERE UNA CADENA QUE REPRESENTE EL PERMISO ASIGNADO";
}
