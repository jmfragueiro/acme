package ar.com.acme.bootstrap.common;

/**
 * Esta clase debe ser utilizada como un punto focal para todos los manejos de textos dentro del sistema,
 * de manera de tener encasulada, en una sola clase, todas las cuestiones asociadas a este tipo de valores.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Constantes {
    // /******************************************************************************************
    //  ** VALORES GLOBALES PARA UTIIZACION EN LOS FILTROS DE BUSQUEDA
    //  ******************************************************************************************/
    // public static final String BUS_SEPARADOR_CLAVE_VALOR = ",";
    // public static final String BUS_SEPARADOR_PARES = ";";
    // public static final String BUS_SEPARADOR_BETWEEN = "*";
    // public static final String BUS_SEPARADOR_WSTAB_REGEX = "\\s+";

    // /******************************************************************************************
    //  ** VALORES GLOBALES DE CONFIGURACION DEL SISTEMA
    //  ******************************************************************************************/
    // public static final int SYS_TEXTCHANGEDELAY = 900;
    // public static final int SYS_MINLENGTHCOMBOCHANGE = 3;
    // public static final Character SYS_CHARFORADDONCOMBO = '+';
    // public static final int CONFIG_ORDEN_DIFF_DIAS_DEVOL = 0;
    // public static final int CONFIG_ORDEN_CABECERA_ACTIVA = 1;

    // /******************************************************************************************
    //  ** CONSTANTES GENERALES DEL SISTEMA A REALIZARSE
    //  ** NOTA: estas variables deben setearse para cada sistema en "resources/sistema.properties"
    //  ******************************************************************************************/
    public static final String SYS_APP_REGEXP_EMAIL = ".{3,}@acme.cl$";

    // /******************************************************************************************
    //  ** CADENAS GENERALES DEL SISTEMA A REALIZARSE
    //  ******************************************************************************************/
    // public static final String SYS_CAD_NOBODY = "?NADIE?";
    public static final String SYS_CAD_NULL = "";
    public static final String SYS_CAD_LOGSEP = ":";
    // public static final String SYS_CAD_NUMSEP = "-";
    // public static final String SYS_CAD_BARRA = "/";
    // public static final String SYS_CAD_BARRAINV = "\\";
    // public static final String SYS_CAD_POSITIVO = "+";
    // public static final String SYS_CAD_NEGATIVO = "-";
    // public static final String SYS_CAD_ALL = "*";
    public static final String SYS_CAD_URLALL = "/**";
    public static final String SYS_CAD_SPACE = " ";
    // public static final String SYS_CAD_COMMA = ",";
    // public static final String SYS_CAD_PUNTO = ".";
    // public static final String SYS_CAD_FINLOG = ";";
    // public static final String SYS_CAD_OPENREF = "<";
    public static final String SYS_CAD_CLOSEREF = ">";
    public static final String SYS_CAD_OPENTYPE = "[";
    public static final String SYS_CAD_CLOSETPE = "]";
    // public static final String SYS_CAD_OPENPARS = "[";
    // public static final String SYS_CAD_CLOSEPARS = "]";
    // public static final String SYS_CAD_PESOS = "$";
    public static final String SYS_CAD_REFER = "->";
    // public static final String SYS_CAD_REMARK = " ***** ";
    // public static final String SYS_CAD_EXCLAM = "!!!!";
    // public static final String SYS_CAD_MONEY = "$";
    // public static final String SYS_CAD_PERCENT = "%";
    // public static final String SYS_CAD_SEP_DESC = ": ";
    // public static final String SYS_CAD_LOGlLINE = "##############################################";
    // public static final String SYS_CAD_MORE = "...";
    public static final String SYS_CAD_UNSESION = "UNKNOW";
    public static final String SYS_CAD_NEW = "NUEVO";
    public static final String SYS_CAD_SUCCESS = "Operación ejecutada satisfactoriamente...";
    public static final String SYS_CAD_FAIL = "No se ha podido ejecutar la operación...";
    // public static final String SYS_CAD_OK = "OK";
    // public static final String SYS_CAD_ACTION_ADD = "add";
    // public static final String SYS_CAD_ACTION_DEL = "delete";
    // public static final String SYS_CAD_ACTION_UPD = "update";
    // public static final String SYS_CAD_CRLF = "\n";
    // public static final String SYS_CAD_APPLISTERROR = "ERROR:".concat(SYS_CAD_CRLF);
    // public static final String SYS_CAD_TXTNULL = "NULO";
    // public static final String SYS_CAD_TXTTOTAL = "TOTAL";
    // public static final String SYS_CAD_TXTREGS = "REGISTROS";
    // public static final String SYS_CAD_FECHADESDE = "Fecha Desde";
    // public static final String SYS_CAD_FECHAHASTA = "Fecha Hasta";
    // public static final String SYS_CAD_SOLOACTIVO = "Solo Activos";
    // public static final String SYS_CAD_UNKNOW = "UNKNOW";
    // public static final String SYS_APP_CAD_YES = "SI";
    // public static final String SYS_APP_CAD_NO = "NO";
    // public static final String SYS_APP_CAD_ID = "ID";
    public static final String SYS_CAD_ERROR = "ERROR:";
    // public static final String SYS_APP_CAD_IMAGE = "IMAGEN";
    // public static final String SYS_APP_CAD_QTY = "Cantidad";
    // public static final String SYS_APP_EXPIRED = "EXPIRED";
    // public static final String SYS_CAD_VERDADERO = "true";
    // public static final String SYS_CAD_FALSO  = "false";
    // public static final String SYS_CAD_SINOBSERVACIONES  = "Sin Observaciones";
    public static final String SYS_CAD_AUTH_URL = "/auth";
    public static final String SYS_CAD_USER_URL = "/usuario";
    public static final String SYS_CAD_LOGGIN_URL = "/login";
    public static final String SYS_CAD_LOGGOUT_URL = "/logout";
    // public static final String SYS_CAD_GETTOKEN_URL = "/tokenuser";
    // public static final String SYS_CAD_RESET_URL = "/reset";
    // public static final String SYS_CAD_PROCRESET_URL = "/procesarreset";
    // public static final String SYS_CAD_CHECKMAIL_URL = "/procesarcheckmail";
    // public static final String SYS_CAD_EMAIL_URL = "/email";
    // public static final String SYS_CAD_FILEMAN_URL = "/filemanager";
    // public static final String SYS_CAD_CODES_URL = "/qr";
    // public static final String SYS_CAD_PREFIJOCLASE_PREFIJO = "prefijo";
    // public static final String SYS_CAD_PREFIJOCLASE_CLASE = "clase";
    // public static final String SYS_CAD_NO_MD5 = "IMPOSIBLEOBTENER";
    // public static final String SYS_CAD_MAIL_NORESPONDER = "noresponder@posadas.gov.ar";
    public static final String SYS_CAD_HTTPAUTH_BASIC = "BASIC";
    public static final String SYS_CAD_HTTPAUTH_BEARER = "BEARER";
    public static final String SYS_CAD_TXTLOGGIN_USER = "username";
    public static final String SYS_CAD_TXTLOGGIN_PASS = "password";

    // /**********************************************************
    //  * Cadenas asociadas a entornos disponibles
    //  **********************************************************/
    // public static final String SYS_CAN_ENV_PROD  = "PRODUCCION";
    // public static final String SYS_CAN_ENV_DEV   = "DESARROLLO";
    // public static final String SYS_CAN_ENV_TEST  = "TESTING";
    // public static final String SYS_CAN_ENV_PRE   = "PRE-PRODUCCION";

    // /**********************************************************
    //  * Cadenas asociadas a las credenciales de usuario
    //  **********************************************************/
    // public static final String CRED_DISPOSITIVO_CONSULTA_OCULTO  = "DISPOSITIVO_OCULTO_VER";

    // /**********************************************************
    //  * Cadenas asociadas a los tipos de contenido http
    //  **********************************************************/
    public static final String SYS_CAD_APP_MIMETYPE_JSON = "application/json";
    // public static final String SYS_APP_MIMETYPE_JSCRIPT = "application/javascript";
    // public static final String SYS_APP_MIMETYPE_PDF = "application/pdf";
    // public static final String SYS_APP_MIMETYPE_SQL = "application/sql";
    // public static final String SYS_APP_MIMETYPE_XML = "application/xml";
    // public static final String SYS_APP_MIMETYPE_ZIP = "application/zip";
    // public static final String SYS_APP_MIMETYPE_MPEG = "audio/mpeg";
    // public static final String SYS_APP_MIMETYPE_GIF = "image/gif";
    // public static final String SYS_APP_MIMETYPE_JPEG = "image/jpeg";
    // public static final String SYS_APP_MIMETYPE_PNG = "image/png";
    // public static final String SYS_APP_MIMETYPE_FORM = "multipart/form-data";
    // public static final String SYS_APP_MIMETYPE_CSS = "text/css";
    // public static final String SYS_APP_MIMETYPE_CSV = "text/csv";
    // public static final String SYS_APP_MIMETYPE_HTML = "text/html";
    // public static final String SYS_APP_MIMETYPE_TXT = "text/plain";
    // public static final String SYS_APP_MIMETYPE_TXTXML = "text/xml";

    // /**********************************************************
    //  * Cadenas asociadas al manejo de formatos de fecha
    //  **********************************************************/
    // public static final String SYS_APP_DATEFORMAT_VIEW = "dd/MM/yyyy";
    // public static final String SYS_APP_DATEFORMAT_DB = "yyyy-MM-dd";
    // public static final String SYS_APP_DATEFORMAT_LIST = "dd/MM/yyyy";//"%1$td/%1$tm/%1$tY";
    // public static final String SYS_APP_DATEFORMAT_REGEX = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";

    // /**********************************************************
    //  * Cadenas asociadas al Inicio / Cierre del Sistema
    //  **********************************************************/
    // public static final String SYS_APP_UIINIT = "Estableciendo interfaz de usuario principal";
    // public static final String SYS_APP_UIOK = "Interfaz de usuario principal establecida: OK";

    // /**********************************************************
    //  * Cadenas del proceso de conexion al Sistema
    //  **********************************************************/
    // public static final String SYS_APP_HTTP_VALI_CAD = "Validation";
    public static final String SYS_CAD_HTTP_AUTH = "Authorization";

    // public static final String SYS_APP_TXTLOGGIN_EMAIL = "Correo Electronico";
    // public static final String SYS_APP_TXTLOGGIN_SIGNIN = "INGRESAR";
    // public static final String SYS_APP_TXTLOGGIN_REMEM = "Recordarme";
    // public static final String SYS_APP_TXTLOGGIN_WELCOME = "Bienvenido a ";
    // public static final String SYS_APP_TXTLOGGIN_NOAUTH = "No Autorizado";
    public static final String MSJ_REQ_ERR_BADREQUEST = "FORMATO DE REQUERIMIENTO DE ACCESO INVALIDO";
    public static final String MSJ_REQ_ERR_BADREQUESTVALUE = "REQUERIMIENTO DE ACCESO INVALIDO";
    // public static final String MSJ_SES_ERR_BADREQAUTH = "ERROR AL AUTENTICAR LA SOLICITUD";
    // public static final String MSJ_ERR_BADFORMATREQUEST = "FORMATO DE REQUERIMIENTO INVALIDO";
    // public static final String MSJ_ERR_UNAUTHORIZED = "Unauthorized";
    // public static final String MSJ_SEC_ERR_GENERICO = "HA OCURRIDO UN ERROR INESPERADO";
    // public static final String MSJ_SEC_ERR_NOT_IMPLEMENTED = "FUNCIONALIDAD NO IMPLEMENTADA";

    // /**********************************************************
    //  * Cadenas de cambio de password
    //  **********************************************************/
    // public static final String SYS_APP_CHANGEPASS_OLDPASS = "Contraseña Actual";
    // public static final String SYS_APP_CHANGEPASS_NEWPASS = "Nueva Contraseña";
    // public static final String SYS_APP_CHANGEPASS_REPPASS = "Repita Nueva Contraseña";
    // public static final String SYS_APP_CHANGEPASS_ERR_BADPASS = "LAS CONTRASEÑA ACTUAL NO ES CORRECTA";
    // public static final String SYS_APP_CHANGEEMAIL_ERR_BADEMAIL = "EL EMAIL NO ES CORRECTO";
    // public static final String SYS_APP_CHANGEPASS_ERR_DISTPASS = "LAS CONTRASEÑAS NUEVAS NO COINCIDEN";
    // public static final String MSJ_APP_CHANGEPASS_ERR_ONCHANGE = "ERROR AL INTENTAR MODIFICAR LA CONTRASEÑA DEL USUARIO";
    // public static final String MSJ_APP_CHANGEEMAIL_ERR_ONCHANGE = "ERROR AL INTENTAR MODIFICAR EL EMAIL DEL USUARIO";
    // public static final String MSJ_APP_CHANGEPASS_INF_CHANGEOK = "SE HA MODIFICADO LA CONTRASEÑA DEL USUARIO";

    // /**********************************************************
    //  * Cadenas de acciones autorizables
    //  **********************************************************/
    // public static final String SYS_AUTH_CAD_VIEWDIT = "viewdit";
    // public static final String SYS_AUTH_CAD_LIST = "list";
    // public static final String SYS_AUTH_CAD_LISTAR = "listar";
    // public static final String SYS_AUTH_CAD_ADD = "add";
    // public static final String SYS_AUTH_CAD_UPDATE = "update";
    // public static final String SYS_AUTH_CAD_DELETE = "delete";
    // public static final String SYS_AUTH_CAD_FILTER = "filtrar";

    // /**********************************************************
    //  * Cadenas de Extensiones de archivo comunes
    //  **********************************************************/
    // public static final String SYS_FILEXT_TXT = ".txt";
    // public static final String SYS_FILEXT_IMP = ".imp";

    // /**********************************************************
    //  * Cadenas de Mensajes del Logging comunes
    //  **********************************************************/
    // public static final String MSJ_LOG_INF_INITLOGG = "INICIANDO SISTEMA DE LOGGING";
    // public static final String MSJ_LOG_INF_ENDLOGG = "FINALIZANDO SISTEMA DE LOGGING";

    // /**********************************************************
    //  * Cadenas de Mensajes de Seguridad y Sesiones comunes
    //  **********************************************************/
    public static final String MSJ_SES_INF_LOGGON = "HA INICIADO CORRECTAMENTE LA SESION DE USUARIO";
    public static final String MSJ_SES_INF_LOGGOFF = "HA FINALIZANDO LA SESION DE USUARIO";
    public static final String MSJ_SES_ERR_LOGIN = "ERROR AL INICIAR LA SESION DE USUARIO";
    public static final String MSJ_SES_ERR_LOGOFF = "ERROR AL FINALIZAR LA SESION DE USUARIO";
    // public static final String MSJ_SES_ERR_ATVERIF = "ERROR AL AUTENTICAR LA SESION DE USUARIO";
    // public static final String MSJ_SES_ERR_NOAUTH = "NO ESTAS AUTORIZADO PARA ACCEDER AL RECURSO SOLICITADO";
    public static final String MSJ_USR_ERR_NOACCES = "ACCESO AL RECURSO O FUNCION NO PERMITIDO";
    // public static final String MSJ_SEC_ERR_NOUSER = "EL USUARIO SOLICITADO NO EXISTE";
    // public static final String MSJ_SEC_ERR_NOUSER_BY_TOKEN = "NO EXISTE NINGUN REGISTRO DE RECUPARACION DE CLAVE SEGUN LA SOLICITUD EFECTUADA";
    // public static final String MSJ_SEC_ERR_SESSIONEXPIRED = "LA SESION DE USUARIO HA EXPIRADO";
    // public static final String MSJ_SEC_ERR_USEREMAILNOTCHECK = "LA CUENTA DEL USUARIO NO TIENE CORREO ELECTRONICO VERIFICADO";
    // public static final String MSJ_SEC_ERR_USEREXPIRED = "LA CUENTA DEL USUARIO SOLICITADO ESTA EXPIRADA";
    // public static final String MSJ_SEC_ERR_TIMEEXPIRED = "EL TIEMPO DE LA ACCION SOLICITADA HA EXPIRADO";
    // public static final String MSJ_SEC_ERR_USERLOGED = "EL USUARIO YA SE ENCUENTRA LOGEADO";
    // public static final String MSJ_SEC_ERR_USERENABLED = "EL USUARIO SOLICITADO SE ENCUENTRA INHABILITADO";
    public static final String MSJ_USR_ERR_USERLOCKED = "EL USUARIO SOLICITADO SE ENCUENTRA BLOQUEADO";
    public static final String MSJ_USR_ERR_USERNOINIT = "ERROR AL VALIDAR LA SESION DEL USUARIO";
    public static final String MSJ_USR_ERR_USERNOTENABLED = "EL USUARIO SOLICITADO SE ENCUENTRA INHABILITADO";
    // public static final String MSJ_SEC_ERR_NO_ACTIVEUSER = "ACTUALMENTE NO EXISTE UN USUARIO ACTIVO";
    // public static final String MSJ_SEC_ERR_USER_NOTLOGGED = "NO EXISTE UNA SESION ACTIVA PARA EL USUARIO";
    // public static final String MSJ_SEC_ERR_USERCANTOP = "ACCESO A OPERAR EL SISTEMA NO PERMITIDO";
    // public static final String MSJ_SEC_ERR_USERNOOPEN = "ERROR AL OBTENER LA SESION DEL USUARIO";
    // public static final String MSJ_ERR_CANTSETEXTRADATA = "ERROR AL DECODIFICAR DATOS EXTRAS EN LA LLAMADA";

    // public static final String MSJ_SEC_INF_TOKEREPODEL = "SE HA REMOVIDO DEL REPOSITORIO UN TOKEN EXISTENTE";
    //
    public static final String MSJ_SES_ERR_NOGRANTS = "NO PUEDEN ESTABLECERSE LOS PERMISOS DEL USUARIO";
    public static final String MSJ_SES_ERR_BADCREDENTIAL = "USUARIO O CONTRASEÑA INCORRECTOS";
    public static final String MSJ_SES_ERR_USERNOAUTH = "USUARIO NO AUTENTICADO";
    public static final String MSJ_SES_ERR_INVALIDTOKEN = "NO SE HA ENCONTRADO UN TOKEN DE USUARIO VALIDO";
    public static final String MSJ_SES_ERR_NOACTIVETOKEN = "NO SE HA ENCONTRADO UNA SESION DE USUARIO ACTIVA";
    public static final String MSJ_TOK_ERR_BADJWTSIGN = "FIRMA DE TOKEN INVALIDA";
    public static final String MSJ_TOK_ERR_BADJWT = "DATOS DE TOKEN INCORRRECTOS";
    public static final String MSJ_TOK_ERR_TOKENNOTSUP = "TOKEN NO SOPORTADO POR LA PLATAFORMA";
    public static final String MSJ_TOK_ERR_EMPTYCLAIM = "CADENA DE CONTENIDO DE TOKEN VACIA";
    public static final String MSJ_TOK_ERR_CANTEXTRACTTED = "NO SE OBTUVO NINGUN VALOR EXTRA DESDE EL TOKEN";
    public static final String MSJ_TOK_ERR_BADTOKEN = "FORMATO DE TOKEN INVALIDO";
    public static final String MSJ_TOK_ERR_TOKENUSERNOOP = "EL ESTADO DEL USUARIO NO PERMITE GENERAR UN TOKEN VALIDO";
    public static final String MSJ_TOK_ERR_TOKENREINIT = "ERROR AL INTENTAR REVALIDAR UN TOKEN";
    public static final String MSJ_TOK_ERR_NOAUTHCAD = "SE REQUIERE UNA CADENA QUE REPRESENTE EL PERMISO ASIGNADO";

    // /**********************************************************
    //  * Cadenas asociadas al envio de correo desde el sistema
    //  **********************************************************/
    // public static final String MSJ_MAIL_ERR_INVALIDTO = "NO SE HAN PROPORCIONADO DESTINATARIOS VALIDOS PARA EL ENVIO DEL CORREO";
    // public static final String MSJ_MAIL_ERR_BADTO = "SE HAN ENCONTRADO DIRECCIONES DE DESTINATARIOS INVALIDAS PARA EL CORREO";
    // public static final String MSJ_MAIL_ERR_FATAL = "ERROR FATAL DURANTE EL ENVIO DEL CORREO";
    // public static final String MSJ_MAIL_SEND_OK = "CORREO ELECTRONICO ENVIADO";
    // public static final String MSJ_MAIL_ERR_NOSUBJECT = "NO SE DEFINIO EL ASUNTO DEL CORREO";
    // public static final String MSJ_MAIL_ERR_NOMESSAGE = "NO SE DEFINIO EL TEXTO DEL MENSAJE PARA INCLUIR EN EL CORREO";
    // public static final String MSJ_MAIL_ERR_NOTO = "NO HAY DESTINATARIOS DEFINIDOS PARA ENVIAR EL CORREO";
    // public static final String MSJ_MAIL_ERR_SEND = "ERROR AL ENVIAR EL CORREO A UNA DIRECCION";

    // /**********************************************************
    //  * Cadenas asociadas a los tipos ROLES BASICOS DEL SISTEMA
    //  **********************************************************/
    // public static final String SYS_APP_ROLE_ADMIN = "ADMIN";
    // public static final String SYS_APP_ROLE_BASIC = "BASIC";
    // public static final String SYS_APP_ROLE_USER = "USER";

    // /**********************************************************
    //  * Cadenas asociadas a las opciones del Sistema
    //  **********************************************************/
    // public static final String SYS_APP_CAD_ACCESS = "Ver";
    // public static final String SYS_APP_CAD_LISTAR = "Listar";
    // public static final String SYS_APP_CAD_ADD = "Agregar";
    // public static final String SYS_APP_CAD_EDIT = "Editar";
    // public static final String SYS_APP_CAD_DEL = "Eliminar";

    // /**********************************************************
    //  * Cadenas de Mensajes de Error comunes
    //  **********************************************************/
    // public static final String MSJ_ERR_UNKNOW = "ERROR DESCONOCIDO";
    // public static final String MSJ_ERR_ABSTRACT = "ERROR AL INTENTAR EJECUTAR COMPORTAMIENTO NO IMPLEMENTADO";
    // public static final String MSJ_ERR_FATALINIT = "ERROR FATAL AL INICIAL EL SISTEMA";
    public static final String MSJ_ERR_EXCEPCION = "ERROR INTERNO DEL SISTEMA";
    // public static final String MSJ_ERR_NOAPPNAME = "NOMBRE DE APLICACION NO ESTABLECIDO";
    // public static final String MSJ_ERR_NOAPPTITLE = "TITULO DE APLICACION NO ESTABLECIDO";
    // public static final String MSJ_ERR_NOAPPVERSION = "VERSION DE APLICACION NO ESTABLECIDA";
    // public static final String MSJ_ERR_NOAPPDB = "BASE DE DATOS NO ESTABLECIDA";
    // public static final String MSJ_ERR_NOAPPPATH = "PATH DE APLICACION NO ESTABLECIDO";
    // public static final String MSJ_ERR_NOAPPIMAGE = "IMAGEN DE APLICACION NO ESTABLECIDA";
    // public static final String MSJ_ERR_ATVERIFYDATA = "ERROR AL INTENTAR VALIDAR UN DATO";
    // public static final String MSJ_ERR_TYPEPARAM = "ERROR AL INTENTAR ASIGNAR UN TIPO DE PARAMETRO NO PERMITIDO";
    // public static final String MSJ_ERR_NULLFILTER = "ERROR AL INTENTAR USAR UN FILTRO NULO";
    // public static final String MSJ_ERR_BADDATEFORMAT = "ERROR AL INTENTAR USAR UN FORMATO DE FECHA";
    // public static final String MSJ_ERR_ONIMAGEGEN = "ERROR AL INTENTAR GENERAR UNA IMAGEN DE SALIDA";

    // /**********************************************************
    //  * Cadenas de Mensajes de REPORTES
    //  **********************************************************/
    // public static final String MSJ_ERR_CANTLOADREPORT = "ERROR AL INTENTAR OBTENER UNA CLASE DE REPORTE";
    // public static final String MSJ_ERR_REPORTITEMNOTFOUND = "ERROR AL INTENTAR OBTENER EL ELEMENTO BASE DE UN REPORTE";

    // /**********************************************************
    //  * Cadenas de Mensajes de AUDITORIA
    //  **********************************************************/
    // public static final String MSJ_AUD_SAVEMARK = "ESTABLECIENDO MARCA DE AUDITORIA";
    // public static final String MSJ_AYD_ERR_ATAUDITDATA = "ERROR AL ESTABLECER AUDITORIA SOBRE UN DATO";
    // public static final String MSJ_AUD_MARKSAVED = "MARCA DE AUDITORIA REALIZADA";

    // /**********************************************************
    //  * Texto de Opciones generales de Menú del Sistema
    //  **********************************************************/
    // public static final String SYS_MEN_ALIGN = "Ubicación de Menú";
    // public static final String SYS_MEN_MENUCHPASS = "Modificar Contraseña";
    // public static final String SYS_MEN_MENUEXIT = "Cerrar Sesión";

    // /**********************************************************
    //  * Constantes de Mensajes de acciones del sistema
    //  **********************************************************/
    // public static final String MSG_ACC_CREATEVIEW = "Creando Vista del Sistema";
    // public static final String MSG_ACC_OPENVIEW = "Abriendo Vista del Sistema";
    // public static final String MSG_ACC_INITVIEW = "Iniciando Vista del Sistema";
    // public static final String MSG_ACC_INITPREVIEW = "Iniciando Preview del Sistema";
    // public static final String MSG_ACC_SETPREVIEW = "Estableciendo Preview a una Vista";
    // public static final String MSG_ACC_SETFORMCRUD = "Estableciendo Form CRUD a una Vista";
    // public static final String MSJ_INF_UPDATEVIEWCOMPONENTS = "Actualizando visualizacion de componentes visuales";
    // public static final String MSJ_INF_UPDATECOMPONENTS = "Actualizando habilitacion de componentes visuales";
    // public static final String MSJ_INF_BOTONSECVERIFY = "Verificando permiso sobre BOTON";
    // public static final String MSJ_INF_BOTONENABILITY = "Verificando habilitacion de BOTON";
    // public static final String MSJ_INF_UPDATECTDTIT = "Actualizando la cantidad de registros en el titulo de la lista";

    // /**********************************************************
    //  * Cadenas de mensajes de consultas al usuario
    //  **********************************************************/
    // public static final String CRUD_MSG_BOXSAVE = "Desea guardar los cambios de este Registro?";
    // public static final String CRUD_MSG_BOXSAVECHANGES = "Desea guardar los cambios realizados?";
    // public static final String CRUD_MSG_BOXSAVEPPAL = "Desea guardar los cambios al Registro Principal?";
    // public static final String CRUD_MSG_BOXADDNEW = "Desea agregar un nuevo Registro de este tipo?";
    // public static final String CRUD_MSG_BOXDEL = "Desea eliminar este Registro?";
    // public static final String CRUD_MSG_BOXQUITL = "Desea quitar este Registro de la lista?";
    // public static final String CRUD_MSG_BOXCANCEL = "Desea descartar los cambios de este Registro?";
    // public static final String CRUD_MSG_BOXOK = "Los datos han sido guardados correctamente!";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de persistencia
    //  **********************************************************/
    // public static final String MSJ_ERR_ATCONSVALDATA = "ERROR AL VALIDAR UNA ENTIDAD AL INTENTAR PERSISTIR:";
    public static final String MSJ_REP_ERR_ATSAVEDATA = "ERROR AL INTENTAR PERSISTIR UN DATO";
    // public static final String MSJ_ERR_ATDELDATA = "ERROR AL INTENTAR ELIMINAR UN DATO";
    // public static final String MSJ_ERR_ATUNDELDATA = "ERROR AL INTENTAR REACTIVAR UN DATO";
    // public static final String MSJ_ERR_ATREFRESHDATA = "ERROR AL INTENTAR RECARGAR UN DATO";
    // public static final String MSJ_ERR_ATLOADDATA = "ERROR AL INTENTAR CARGAR UN DATO";
    // public static final String MSJ_ERR_OPTIMISTLOCK = "ERROR DE BLOQUEO OPTIMISTA EN EL MOTOR DE PERSISTENCIA!";
    // public static final String MSJ_ERR_CONSTRAINTVIOLATION = "ERROR DE VIOLACION DE RESTRICCION DE DATOS!";
    // public static final String MSJ_ERR_TARGETINVOCATION = "ERROR DE CONFIGURACION DE ENTIDADES DE PERSISTENCIA";
    // public static final String MSJ_ERR_SQLEXEC = "ERROR AL EJECUTAR CONSULTA AL MOTOR DE PERSISTENCIA";
    // public static final String MSJ_ERR_GENERICEXCEPTION = "ERROR GENERICO DE PERSISTENCIA";
    // public static final String MSJ_ERR_VERIFYEXCEPTION = "ERROR DETERMINANDO EL TIPO DE EXCEPCION DE PERSISTENCIA";
    // public static final String MSJ_ERR_VRFYCVEXCEPTION = "ERROR DETERMINANDO LA EXCEPCION 'CONSTRAINT VALIDATION'";
    // public static final String MSJ_ERR_VRFYTIEXCEPTION = "ERROR DETERMINANDO LA EXCEPCION 'TARGET INVOCATION'";

    // /**********************************************************
    //  * Cadenas de Mensajes de persistencia
    //  **********************************************************/
    // public static final String MSJ_INF_FETCH_FROMBACKEND = "Obteniendo datos desde el Backend";
    // public static final String MSJ_INF_COUNT_FROMBACKEND = "Contabilizando datos desde el Backend";
    // public static final String MSJ_INF_REFRESHALL = "Refrescando lista de datos de persistencia a Dataprovider";

    // /**********************************************************
    //  * Cadenas de Mensajes de errores con Entidades
    //  **********************************************************/
    // public static final String MSJ_DB_LOADDATA = "OBTENIENDO ENTIDAD";
    // public static final String MSJ_DB_SAVEDATA = "PERSISTIENDO ENTIDAD";
    // public static final String MSJ_DB_ALTERDATA = "PERSISTIENDO ACTUALIZACION";
    // public static final String MSJ_DB_NORMALIZEDATA = "NORMALIZANDO DATOS A PERSISTIR";
    // public static final String MSJ_DB_REFRESHDATA = "REFRESCANDO ENTIDAD";
    // public static final String MSJ_DB_NOREFRESHNEW = "SE OMITIO EL REFRESH DE UNA ENTIDAD NUEVA";
    // public static final String MSJ_DB_DELDATA = "ELIMINANDO ENTIDAD";
    // public static final String MSJ_DB_DELSDATA = "ELIMINANDO ENTIDADES";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de campos de base de datos
    //  **********************************************************/
    // public static final String MSJ_ERR_DB_FIELD_EMPTY = "DEBEN CARGARSE DATOS PARA EL CAMPO OBLIGATORIO: ";
    // public static final String MSJ_ERR_DB_FIELD_NOK = "DEBEN CARGARSE DATOS VALIDOS PARA EL CAMPO: ";
    // public static final String MSJ_ERR_DB_FIELD_LONGNOK = "LA LONGITUD DE DATOS INGRESADOS ES INVALIDA PARA EL CAMPO: ";
    // public static final String MSJ_ERR_DB_ADDFIELD_NULL = "EL ELEMENTO QUE DESEA AGREGAR ES NULO!";
    // public static final String MSJ_ERR_DB_EDITFIELD_NULL = "EL ELEMENTO QUE DESEA EDITAR ES NULO!";
    // public static final String MSJ_ERR_DB_DELFIELD_NULL = "EL ELEMENTO QUE DESEA QUITAR ES NULO!";
    // public static final String MSJ_ERR_DB_ATT_EXIST = "EL ELEMENTO QUE SE DESEA AGREGAR YA SE ENCUENTRA AGREGADO!";
    // public static final String MSJ_ERR_DB_PARAMTYPE = "EL ATRIBUTO QUE DESEA APLICAR NO TIENE EL TIPO CORRECTO!";
    // public static final String MSJ_ERR_DB_PARAMVALUE = "EL ATRIBUTO QUE DESEA APLICAR NO TIENE EL VALOR CORRECTO!";
    // public static final String MSJ_ERR_DB_EDITFIELD = "NO SE PUEDE ELIMINAR EL ELEMENTO DE LA BASE DE DATOS!";
    // public static final String MSJ_ERR_DB_REFRESHITEM = "ERROR AL REFRESCAR LA INFORMACION DEL ELEMENTO SELECCIONADO!";
    // public static final String MSJ_ERR_DB_VERIFYFIELD = "SE HAN DETECTADO ERRORES VALIDANDO LA CARGA DE DATOS!";
    // public static final String MSJ_ERR_DB_ATSAVEDATA = "SE HAN DETECTADO ERRORES GUARDANDO LOS DATOS!";
    // public static final String MSJ_ERR_DB_CANTVERIFYFIELD = "NO SE HA PODIDO VERIFICAR LA CARGA DE DATOS!";
    public static final String MSJ_REP_ERR_NOITEM = "NO PUDO OBTENERSE EL ITEM DESEADO!";
    public static final String MSJ_REP_ERR_TOOMANY = "SE OBTUVIERON MAS ITEMS QUE LOS DESEADOS!";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de conversion de campos
    //  **********************************************************/
    // public static final String MSJ_ERR_DB_ATCONVERTDATA = "SE HAN DETECTADO ERRORES CONVIERTIENDO TIPOS DE DATOS: ";
    // public static final String MSJ_ERR_DB_NEEDINTEGER = "DEBE INGRESAR UN NUMERO ENTERO!";
    // public static final String MSJ_ERR_DB_NEEDDOUBLE = "DEBE INGRESAR UN NUMERO DECIMAL!";
    // public static final String MSJ_ERR_DB_NEEDDATE = "DEBE INGRESAR UNA FECHA VALIDA!";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de validacion de campos
    //  **********************************************************/
    // public static final String MSJ_ERR_REST_VALIDATE = "SE HAN DETECTADO ERRORES VALIDANDO LOS DATOS RECIBIDOS!";

    // /************************************************************
    //  * Cadenas de Mensajes de Errores especificos de base de datos
    //  ***********************************************************/
    // public static final String MSJ_ERR_DB_PARAM_NOEXIST = "EL PARAMETRO O RECURSO DESEADO NO EXISTE!";
    // public static final String MSJ_ERR_DB_OBJETO_NOEXIST = "EL ELEMENTO QUE SE DESEA AGREGAR O VERIFICAR NO EXISTE!";
    // public static final String MSJ_ERR_DB_OBJETO_EXIST = "EL ELEMENTO QUE SE DESEA AGREGAR YA SE ENCUENTRA AGREGADO!";

    // /**********************************************************
    //  * Cadenas de Mensajes de error en las Vistas del Sistema
    //  **********************************************************/
    // public static final String MSJ_ERR_SYSTEMVIEW_NOK = "EL ESTADO DE LAS VISTAS DEL SISTEMA ES ERRONEO";
    // public static final String MSJ_ERR_NONEWITEM = "ERROR AL INTENTAR INSTANCIAR UN NUEVO ITEM PARA LA VISTA";
    // public static final String MSJ_ERR_NOREFRESHITEM = "ERROR AL INTENTAR SINCRONIZAR UN ITEM PARA LA VISTA";
    // public static final String MSJ_ERR_CLASSITEM = "ERROR DE COINCIDENCIA DE TIPO DE ITEM PARA LA VISTA";
    // public static final String MSJ_ERR_BADFORMATVIEW = "ERROR DE FORMAT DE UN ITEM VISUALIZADO";
    // public static final String MSJ_ERR_TAB_WITHOUT_CONTENT = "ERROR AL INTENTAR CREAR UN TAB SIN CONTENIDO";
    // public static final String MSJ_ERR_TAB_PROC_OVERFLOW = "ERROR AL INTENTAR PROCESAR MAS ITEMS DE LOS EXISTENTES EN LA LISTA";
    // public static final String MSJ_ERR_TAB_PROC_BAD_CTDAD = "ERROR AL INTENTAR PROCESAR SIN ITEMS DE LA LISTA";
    // public static final String MSJ_ERR_FCNOTPREENT = "LA FUNCIONALIDAD NO HA SIDO IMPLEMENTADA";
    // public static final String MSJ_ERR_CANTFOCUS = "NO SE PUDO DAR FOCO A UN ATRIBUTO DE LA VISTA";
    // public static final String MSJ_ERR_PREVIEWNOVER = "SE INTENTO ABRIR UN PREVIEW EN MODO DISTINTO A VER";
    // public static final String MSJ_ERR_NOFORM = "ERROR DETERMINANDO EL FORMULARIO A UTILIZAR";
    // public static final String MSJ_ERR_NOPREVIEW = "ERROR DETERMINANDO EL PREVIEW A UTILIZAR";
    // public static final String MSJ_ERR_NOFATHER = "ERROR DETERMINANDO EL OBJETO INICIADOR A UTILIZAR";
    // public static final String MSJ_ERR_BADMODE = "SE HA INTENTADO UTILIZAR UN MODO DE VISTA NO ACEPTADO (SERA TANSFORMADO A 'VER')";
    // public static final String MSJ_ERR_CANTOPENVIEW = "HAY ERRORES EN LA CARGA DEL REGISTRO PRICINPAL, POR FAVOR REVISE LOS DATOS INGRESADOS";
    // public static final String MSJ_ERR_CS_NOITEM = "NO HAY UN ITEM PARA MOSTRAR";
    // public static final String MSJ_ERR_REPO_NOITEM = "NO PUDO OBTENERSE EL ITEM DESEADO";
    // public static final String MSJ_ERR_REPO_TOOMANY = "SE OBTUVIERON MAS ITEMS QUE LOS DESEADOS";
    // public static final String MSJ_ERR_CS_CANTSHOW_ITEM = "NO SE HA PODIDO MOSTRAR EL ELEMENTO";
    // public static final String MSJ_ERR_CS_CANTSHOW_SELECT = "NO SE HA PODIDO ABRIR LA VENTANA DE SELECCION";
    // public static final String MSJ_ERR_CS_CANTSHOW_ADDEDIT = "NO SE HA PODIDO ABRIR LA VENTANA DE AGREGADO/EDICION";
    // public static final String MSJ_ERR_CS_CANTDEL_SELECT = "NO SE PUEDE BLANQUEAR EL CAMPO SELECCIONADO";
    // public static final String MSJ_ERR_CS_CANTSET_SELECT = "NO SE PUEDE ESTABLECER EL VALOR DE LOS CAMPOS DEPENDIENTES";
    // public static final String MSJ_ERR_CS_CANTSHOW_FORM = "NO SE HA PODIDO ABRIR LA VENTANA SELECCIONADA";
    // public static final String MSJ_ERR_CS_CANTSHOW_PPALVIEW = "NO SE HA PODIDO ABRIR UNA VISTA PRINCIPAL";
    // public static final String MSJ_ERR_BAD_PATH = "NO SE HA PODIDO UBICAR EL DIRECTORIO REQUERIDO";
    // public static final String MSJ_ERR_TOOBIG_FILE = "EL ARCHIVO QUE INTENTA TRABAJAR ES DEMASIADO GRANDE";
    // public static final String MSJ_ERR_ONWORK_FILE = "ERROR AL INTENTAR TRABAJAR CON EL DIRECTORIO/ARCHIVO QUE INTENTA UTILIZAR";
    // public static final String MSJ_ERR_ON_CALCVALUE = "ERROR AL INTENTAR CALCULAR EL VALOR RESULTADO";

    // /**********************************************************
    //  * Constantes de Titulos de Ventanas
    //  **********************************************************/
    // public static final String WIN_TIT_SAVEREG = "GUARDAR REGISTRO";
    // public static final String WIN_TIT_SAVECHANGES = "GUARDAR CAMBIOS";
    // public static final String WIN_TIT_SAVEREGPPAL = "GUARDAR REGISTRO PRINCIPAL";
    // public static final String WIN_TIT_ADDNEWITEM = "AGREGAR NUEVO ELEMENTO";
    // public static final String WIN_TIT_DELREG = "BORRAR REGISTRO";
    // public static final String WIN_TIT_QUITREG = "QUITAR REGISTRO";
    // public static final String WIN_TIT_DESCARTREG = "DESCARTAR CAMBIOS";
    // public static final String WIN_TIT_CHANGEPASS = "MODIFICAR CONTRASEÑA";
    // public static final String WIN_TIT_CHANGEMENU = "MODIFICAR UBICACION DE MENU";
    // public static final String WIN_TIT_SHOWIMAGE = "MOSTRAR IMAGEN";

    // /**********************************************************
    //  * Constantes de Reportes
    //  **********************************************************/
    // public static final String MSJ_REP_INF_SETBASEPATH = "ESTABLECIENDO DIRECTORIO BASE DEL REPORTE";
    // public static final String MSJ_REP_INF_BASEPATH = "DIRECTORIO BASE DEL REPORTE ESTABLECIDO";
    // public static final String MSJ_REP_ERR_NOBASEPATH = "ERROR AL INTENTAR ESTABLECER DIRECTORIO BASE DEL REPORTE";
    // public static final String MSJ_REP_INF_INITPARES = "INICIANDO CARGA DE ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_INF_LOAD = "CARGANDO ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_INF_LOADOK = "ARCHVO TEMPLATE DEL REPORTE CARGADO CORRECTAMENTE";
    // public static final String MSJ_REP_ERR_CANTREADTEMPLATE = "ERROR AL INTENTAR LEER EL ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_ERR_CANTLOADTEMPLATE = "ERROR AL INTENTAR ENCONTRAR EL ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_INF_COMPILE = "CARGANDO ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_INF_COMPILEOK = "ARCHVO TEMPLATE DEL REPORTE CARGADO CORRECTAMENTE";
    // public static final String MSJ_REP_ERR_COMPILETEMPLATE = "ERROR AL INTENTAR COMPILAR EL ARCHVO TEMPLATE DEL REPORTE";
    // public static final String MSJ_REP_INF_COMPLETE = "COMBINANDO DATOS DEL REPORTE";
    // public static final String MSJ_REP_INF_COMPLETEOK = "REPORTE COMBINADO CON DATOS CORRECTAMENTE";
    // public static final String MSJ_REP_ERR_COMPLETE = "ERROR AL INTENTAR RELLENAR LOS DATOS EN EL REPORTE";
    // public static final String MSJ_REP_ERR_NOEXPORT = "ERROR AL INTENTAR EXPORTAR EL REPORTE";
    // public static final String MSJ_REP_EXPORT_PDF = "EXPORTANDO REPORTE A PDF";
    // public static final String MSJ_REP_EXPORT_XLS = "EXPORTANDO REPORTE A EXCEL";
    // public static final String MSJ_REP_GENERATING_REPORT = "Generando Reporte...";
    // public static final String MSJ_REP_GENERATING_REPORT_OK = "Reporte generado correctamente";
    // public static final String MSJ_REP_GENERATE_PREOUT_STRING = "_";
    // public static final String MSJ_REP_GENERATE_POSOUT_PDF = ".pdf";
    // public static final String MSJ_REP_GENERATE_POSOUT_XLS = ".xls";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de manejo de archivos del SO
    //  **********************************************************/
    // public static final String MSJ_INF_FILE_INIUP = "INICIANDO LA SUBIDA DEL ARCHIVO";
    // public static final String MSJ_INF_FILE_OKUP = "FINALIZO LA SUBIDA DEL ARCHIVO";
    // public static final String MSJ_INF_FILE_OK = "FINALIZO OK LA OPERACION DE ARCHIVO";
    // public static final String MSJ_ERR_FILE_NULLNAME = "EL NOMBRE DE ARCHIVO NO PUEDE SER NULO";
    // public static final String MSJ_ERR_FILE_NOTEXIST = "EL ARCHIVO CON EL QUE INTENTA TRABAJAR NO EXISTE";
    // public static final String MSJ_ERR_FILE_NOPREVIEW = "EL ARCHIVO NO POSEE PREVISUALIZACION, DESCARGARGUELO";
    // public static final String MSJ_ERR_FILE_CANTUPLOAD = "NO SE PUEDE HACER UPLOAD DEL ARCHIVO";
    // public static final String MSJ_ERR_FILE_CANTDELETE = "NO SE PUEDE BORRAR EL ARCHIVO";
    // public static final String MSJ_ERR_FILE_NODATA = "FALTAN DATOS PARA LA ACCION DE ARCHIVO";
    // public static final String MSJ_ERR_FILE_NOSERVICE = "NO SE HA CONFIGURADO UN SERVICIO DE MANEJO DE ARCHIVOS";
    // public static final String MSJ_ERR_FILE_PREACTION = "NO SE HA PODIDO EJECUTAR LA ACCION PREVIA DE MANEJO DE ARCHIVOS";
    // public static final String MSJ_ERR_FILE_FATAL = "NO SE HA PODIDO EJECUTAR LA ACCION DE MANEJO DE ARCHIVOS";
    // public static final String MSJ_ERR_FILE_NOCONTENT = "NO SE HA OBTENIDO RESPUESTA DE LA ACCION DE MANEJO DE ARCHIVOS";

    // /**********************************************************
    //  * Cadenas de Mensajes de Errores de exportacion/importacion
    //  **********************************************************/
    // public static final String MSJ_IO_ERR_INIT = "INICIANDO UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONINIT = "ERROR AL INICIAR UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_NOHEAD = "ERROR AL DETECTAR LA CABECERA DE UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONPROC = "ERROR AL EJECUTAR UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_BADTYPES = "ERROR DE COMPATIBILIDAD ENTRE ELEMENTOS DE UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_BADEHAD = "ERROR DE FORMATO DE CABECERA EN UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_BADREG = "ERROR DE FORMATO DE REGISTRO EN UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_DATAPOSEND = "ERROR DE DATOS POSTERIORES AL PIE/REGISTRO EN UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONCONFIG = "ERROR AL CONFIGURAR UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONCLEAN = "ERROR AL LIMPIAR UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONPERSIST = "NO SE PUDO PERSISTIR ALGUN DATO DEL PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONLINEPERSIST = "NO SE PUDO PERSISTIR UNA LINEA DEL PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_ONFINALIZE = "ERROR AL LIMPIAR UN PROCESO DE EXP./IMP.";
    // public static final String MSJ_IO_ERR_FINALIZE = "PROCESO DE EXP./IMP. FINALIZADO";
    // public static final String MSJ_IO_CAD_STATUSTITLE = "ESTADO DEL PROCESO: ";
    // public static final String MSJ_IO_CAD_REGSFINDED = "REGISTROS DETECTADOS: ";
    // public static final String MSJ_IO_CAD_PROCFINDED = "PROCESAMIENTO FINALIZADO EXISTOSAMENTE";
    // public static final String MSJ_IO_ERR_ENTIDAD_ORIGEN_NOK = "DEBE SELECCIONARSE UNA ENTIDAD ORIGEN VALIDA";
    // public static final String MSJ_IO_CAD_PROCNOOK = "ERROR EN EL PROCESAMIENTO DE UN ARCHIVO DE EXP./IMP.";

    // /**********************************************************
    //  * Cadenas de referencias para HATEOAS
    //  **********************************************************/
    // public static final String CAD_HATEOAS_POR_REVISTA = "PorRevista";

    // /**********************************************************
    //  * Constantes de Control para Modulo Patrimonio
    //  **********************************************************/
    // public static final Integer COUNT_MAXIMA_COPIA_BIENES = 50;

    // /**********************************************************
    //  * Cadenas de referencias para cuestiones de cliente APIREST
    //  **********************************************************/
    // public static final String MSJ_ERR_REST_FATAL = "ERROR AL INTENTAR UNA LLAMADA DE API REST";
}
