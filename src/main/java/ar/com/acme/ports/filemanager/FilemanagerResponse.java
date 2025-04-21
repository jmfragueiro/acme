package ar.gov.posadas.mbe.ports.filemanager;

import ar.gov.posadas.mbe.framework.common.Constantes;

/**
 * Esta clase es utilizada para registrar el resultado de operaciones sobre archivos,
 * por ejemplo para la devolucion de datos cuando se sube un archivo al repositorio.
 */
public record FilemanagerResponse(boolean ok, int status, String msg, String nombreArchivo, byte[] file, FilemanagerRequest request) {
    public static FilemanagerResponse cantUpload(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       409, //CONFLICT
                                       Constantes.MSJ_ERR_FILE_CANTUPLOAD,
                                       request.filename(),
                                       null,
                                       request);
    }

    public static FilemanagerResponse cantDelete(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       409, //CONFLICT
                                       Constantes.MSJ_ERR_FILE_CANTDELETE,
                                       request.filename(),
                                       null,
                                       request);
    }

    public static FilemanagerResponse noServiceClient(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       503, //SERVICE_UNAVAILABLE
                                       Constantes.MSJ_ERR_FILE_CANTDELETE,
                                       request.filename(),
                                       null,
                                       request);
    }

    public static FilemanagerResponse noDataForRequest(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       204, // NO_CONTENT
                                       Constantes.MSJ_ERR_FILE_NOCONTENT,
                                       request.filename(),
                                       null,
                                       request);
    }

    public static FilemanagerResponse preconditionFailed(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       412, //PRECONDITION_FAILED
                                       Constantes.MSJ_ERR_FILE_PREACTION,
                                       request.filename(),
                                       null,
                                       request);
    }

    public static FilemanagerResponse fatal(FilemanagerRequest request) {
        return new FilemanagerResponse(false,
                                       500, //INTERNAL_SERVER_ERROR
                                       Constantes.MSJ_ERR_FILE_FATAL,
                                       request.filename(),
                                       null,
                                       request);
    }
}
