package ar.gov.posadas.mbe.framework.utils.codes;

public interface ICodeClass {
    /**
     * Este metodo debería retornar verdadero si la clase puede manejar la informacion
     * segun un token enviado como argumento (sea para generar un QR o barcode). Para
     * ello, la clase sabrá como decodificar el token y definir si lo entiende y lo
     * puede manejar.
     *
     * @param token el token que debera ser manejado por la clase
     * @return true si la clase puede manejar ese codigo, de lo contrario false
     */
    boolean isThatCodeClass(String token);


    /**
     * Una clase capaz de manejar un codigo de clase tiene que poder obtener una imagen QR
     * relevante asociada a los datos contenidos en el token. Este es el metodo que permite obtener
     * esa información. Hay que recordar que los 4 primeros caracteres del token recibido
     * identifican el origen deseado de los datos.
     *
     * @param token el token con los datos origen
     * @return una secuencia que representa la imagen QR a partir de la información asociada al token 
     */
    byte[] findQRImageByToken(String token);
}
