package ar.com.acme.bootstrap.common;

/**
 * Esta clase debe ser utilizada como un punto focal para todas los constantes dentro del sistema,
 * de manera de tener encasulada, en una sola clase, todas las cuestiones asociadas a estos valores.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Constantes {
    //  ******************************************************************************************
    //  ** CONSTANTES GENERALES DEL SISTEMA A REALIZARSE
    //  ******************************************************************************************
    public static final String SYS_APP_REGEXP_EMAIL = ".{3,}@acme.cl$";

    //  ******************************************************************************************
    //  ** CADENAS BASICAS NECESARIAS DEL SISTEMA A REALIZARSE
    //  ******************************************************************************************
    public static final String SYS_CAD_NULL = "";
    public static final String SYS_CAD_LOGSEP = ":";
    public static final String SYS_CAD_URLALL = "/**";
    public static final String SYS_CAD_SPACE = " ";
    public static final String SYS_CAD_CLOSEREF = ">";
    public static final String SYS_CAD_OPENTYPE = "[";
    public static final String SYS_CAD_CLOSETPE = "]";
    public static final String SYS_CAD_REFER = "->";
    public static final String SYS_CAD_UNSESION = "UNKNOW";
    public static final String SYS_CAD_NEW = "NUEVO";
    public static final String SYS_CAD_SUCCESS = "Operación ejecutada satisfactoriamente...";
    public static final String SYS_CAD_FAIL = "No se ha podido ejecutar la operación...";
    public static final String SYS_CAD_ERROR = "ERROR:";
    public static final String SYS_CAD_AUTH_URL = "/auth";
    public static final String SYS_CAD_USER_URL = "/usuario";
    public static final String SYS_CAD_LOGGIN_URL = "/login";
    public static final String SYS_CAD_LOGGOUT_URL = "/logout";
    public static final String SYS_CAD_HTTPAUTH_BASIC = "BASIC";
    public static final String SYS_CAD_HTTPAUTH_BEARER = "BEARER";
    public static final String SYS_CAD_TXTLOGGIN_USER = "username";
    public static final String SYS_CAD_TXTLOGGIN_PASS = "password";
    public static final String SYS_CAD_HTTP_AUTH = "Authorization";

    //  **********************************************************
    //  * CADENAS ASOCIADAS A LOS TIPOS DE CONTENIDO HTTP
    //  **********************************************************
    public static final String SYS_CAD_APP_MIMETYPE_JSON = "application/json";
    public static final String SYS_CAD_APP_MIMETYPE_FORM = "multipart/form-data";

    //  **********************************************************
    //  * CADENAS ASOCIADAS A LOS PROCESOS DE REQUEST/RESPONSE
    //  **********************************************************
    public static final String MSJ_REQ_ERR_BADREQUEST = "FORMATO DE REQUERIMIENTO DE ACCESO INVALIDO";
    public static final String MSJ_REQ_ERR_BADREQUESTVALUE = "REQUERIMIENTO DE ACCESO INVALIDO";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE SESION COMUNES
    //  **********************************************************
    public static final String MSJ_SES_INF_LOGGON = "HA INICIADO CORRECTAMENTE LA SESION DE USUARIO";
    public static final String MSJ_SES_INF_LOGGOFF = "HA FINALIZANDO LA SESION DE USUARIO";
    public static final String MSJ_SES_ERR_LOGIN = "ERROR AL INICIAR LA SESION DE USUARIO";
    public static final String MSJ_SES_ERR_LOGOFF = "ERROR AL FINALIZAR LA SESION DE USUARIO";
    public static final String MSJ_SES_ERR_NOGRANTS = "NO PUEDEN ESTABLECERSE LOS PERMISOS DEL USUARIO";
    public static final String MSJ_SES_ERR_BADCREDENTIAL = "USUARIO O CONTRASEÑA INCORRECTOS";
    public static final String MSJ_SES_ERR_USERNOAUTH = "USUARIO NO AUTENTICADO";
    public static final String MSJ_SES_ERR_INVALIDTOKEN = "NO SE HA ENCONTRADO UN TOKEN DE USUARIO VALIDO";
    public static final String MSJ_SES_ERR_NOACTIVETOKEN = "NO SE HA ENCONTRADO UNA SESION DE USUARIO ACTIVA";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE USUARIO COMUNES
    //  **********************************************************
    public static final String MSJ_USR_ERR_NOACCES = "ACCESO AL RECURSO O FUNCION NO PERMITIDO";
    public static final String MSJ_USR_ERR_USERLOCKED = "EL USUARIO SOLICITADO SE ENCUENTRA BLOQUEADO";
    public static final String MSJ_USR_ERR_USERNOINIT = "ERROR AL VALIDAR LA SESION DEL USUARIO";
    public static final String MSJ_USR_ERR_USERNOTENABLED = "EL USUARIO SOLICITADO SE ENCUENTRA INHABILITADO";

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

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE ERROR COMUNES
    //  **********************************************************
    public static final String MSJ_ERR_EXCEPCION = "ERROR INTERNO DEL SISTEMA";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DEL PROCESAMIENTO DE PERSISTENCIA
    //  **********************************************************
    public static final String MSJ_REP_ERR_ATSAVEDATA = "ERROR AL INTENTAR PERSISTIR UN DATO";
    public static final String MSJ_REP_ERR_FIELD_EMPTY = "DEBEN CARGARSE DATOS PARA EL CAMPO OBLIGATORIO: ";
    public static final String MSJ_REP_ERR_FIELD_NOK = "DEBEN CARGARSE DATOS VALIDOS PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_FIELD_LONG_NOK = "LA LONGITUD DE DATOS INGRESADOS ES INVALIDA PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_NOITEM = "NO PUDO OBTENERSE EL ITEM DESEADO!";
    public static final String MSJ_REP_ERR_TOOMANY = "SE OBTUVIERON MAS ITEMS QUE LOS DESEADOS!";
}
