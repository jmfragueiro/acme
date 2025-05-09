package ar.com.acme.commons;

public class Constants {
    //  **********************************************************
    //  * CADENAS DE DE TEXTOS COMUNES
    //  **********************************************************
    public static final String SYS_CAD_OPENTYPE = "[";
    public static final String SYS_CAD_CLOSETPE = "]";
    public static final String SYS_CAD_ENTITY_NEW = "NEW";
    public static final String SYS_CAD_REFER = "->";
    public static final String SYS_CAD_NULL = "";
    public static final String SYS_CAD_LOGSEP = ":";
    public static final String SYS_CAD_URLALL = "/**";
    public static final String SYS_CAD_CLOSEREF = ">";
    public static final String SYS_CAD_MSJ_SEPARATOR = ". ";
    public static final String SYS_CAD_SPACE = " ";
    public static final String SYS_CAD_UNSESION = "UNKNOW";
    public static final String SYS_CAD_ERROR = "ERROR:";
    public static final String SYS_CAD_AUTH_URL = "/auth";
    public static final String SYS_CAD_LOGGIN_URL = "/login";
    public static final String SYS_CAD_LOGGOUT_URL = "/logout";
    public static final String SYS_CAD_ERROR_URL = "/error";
    public static final String SYS_CAD_HTTPAUTH_BASIC = "BASIC";
    public static final String SYS_CAD_HTTPAUTH_BEARER = "BEARER";
    public static final String SYS_CAD_HTTP_AUTH = "Authorization";
    public static final String SYS_CAD_RESPONSE_ERROR_MSG = "mensaje";
    public static final String SYS_CAD_FORMATTER_STRING = " (%S)";

    //  **********************************************************
    //  * CADENAS ASOCIADAS A LOS TIPOS DE CONTENIDO HTTP
    //  **********************************************************
    public static final String SYS_CAD_APP_MIMETYPE_JSON = "application/json";

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
    public static final String MSJ_SES_ERR_BADCREDENTIAL = "USUARIO O CONTRASEÑA INCORRECTOS";
    public static final String MSJ_SES_ERR_USERNOAUTH = "USUARIO NO AUTENTICADO";
    public static final String MSJ_SES_ERR_ONAUTH = "ERROR EN EL PROCESO DE AUTENTICACION";
    public static final String MSJ_SES_ERR_INVALIDTOKEN = "NO SE HA ENCONTRADO UN TOKEN DE USUARIO VALIDO";
    public static final String MSJ_SES_ERR_NOACTIVETOKEN = "NO SE HA ENCONTRADO UNA SESION DE USUARIO ACTIVA";
    public static final String MSJ_SES_ERR_USERALREADYLOGGED = "EL USUARIO YA SE ENCUENTRA LOGUEADO EN EL SISTEMA";
    public static final String MSJ_SES_ERR_USERNOTLOGGED = "EL USUARIO NO SE ENCUENTRA LOGUEADO EN EL SISTEMA";
    public static final String MSJ_SES_ERR_USERCANTOP = "EL USUARIO NO PUEDE OPERAR EL SISTEMA ACTUALMENTE";
    public static final String MSJ_SES_ERR_NOACCES = "ACCESO AL RECURSO O FUNCION NO PERMITIDO";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE USUARIO COMUNES
    //  **********************************************************
    public static final String MSJ_USR_ERR_DELETED = "SE INTENTA ACCEDER A UN USUARIO ELIMINADO";
    public static final String MSJ_USR_ERR_USERNOINIT = "ERROR AL VALIDAR LA SESION DEL USUARIO";
    public static final String MSJ_USR_ERR_USERINACTIVE = "EL USUARIO SOLICITADO SE ENCUENTRA INHABILITADO";

    //  **********************************************************
    //  * CADENAS ASOCIADAS AL MANEJO DE TOKENS
    //  **********************************************************
    public static final String MSJ_TOK_ERR_BADJWTSIGN = "FIRMA DE TOKEN INVALIDA";
    public static final String MSJ_TOK_ERR_BADJWT = "DATOS DE TOKEN INCORRRECTOS";
    public static final String MSJ_TOK_ERR_TOKENNOTSUP = "TOKEN NO SOPORTADO POR LA PLATAFORMA";
    public static final String MSJ_TOK_ERR_EMPTYCLAIM = "CADENA DE CONTENIDO DE TOKEN VACIA";
    public static final String MSJ_TOK_ERR_EXPIRED = "TOKEN DE AUTENTICACION EXPIRADO";
    public static final String MSJ_TOK_ERR_BADTOKEN = "FORMATO DE TOKEN INVALIDO";
    public static final String MSJ_TOK_ERR_TOKENREINIT = "ERROR AL INTENTAR REVALIDAR UN TOKEN";
    public static final String MSJ_TOK_ERR_GENERAL = "ERROR DE VALIDACION DE TOKEN";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DE ERROR COMUNES
    //  **********************************************************
    public static final String MSJ_ERR_EXCEPCION = "ERROR INTERNO DEL SISTEMA";
    public static final String MSJ_ERR_UNPROCESSABLE = "EL REQUERIMIENTO NO PUDO PROCESARSE CORRECTAMENTE";

    //  **********************************************************
    //  * CADENAS DE MENSAJES DEL PROCESAMIENTO DE PERSISTENCIA
    //  **********************************************************
    public static final String MSJ_REP_ERR_ATSAVEDATA = "ERROR AL INTENTAR PERSISTIR UN DATO";
    public static final String MSJ_REP_ERR_FIELD_EMPTY = "DEBEN CARGARSE DATOS PARA EL CAMPO OBLIGATORIO: ";
    public static final String MSJ_REP_ERR_FIELD_NOK = "DEBEN CARGARSE DATOS VALIDOS PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_FIELD_LONG_NOK = "LA LONGITUD DE DATOS INGRESADOS ES INVALIDA PARA EL CAMPO: ";
    public static final String MSJ_REP_ERR_NOITEM = "NO PUDO OBTENERSE EL ELEMENTO DESEADO!";
    public static final String MSJ_REP_ERR_NOVALIDATE = "SE HAN ENCONTRADO ERRORES AL VALIDAR UN ITEM";

    //  **********************************************************
    //  * URLs DE LOS CONTROLADORES REST
    //  **********************************************************
    public static final String URL_CONTROLLER_USER = "user";
}
