package ar.com.acme.adapter.common;

public class Constantes {
    //  ******************************************************************************************
    //  ** CONSTANTES GENERALES DEL SISTEMA A REALIZARSE
    //  ******************************************************************************************
    public static final String SYS_APP_REGEXP_EMAIL = ".{3,}@acme.cl$";

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
