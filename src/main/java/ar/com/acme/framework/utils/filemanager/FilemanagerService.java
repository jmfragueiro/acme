package ar.gov.posadas.mbe.framework.utils.filemanager;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Fechas;
import ar.gov.posadas.mbe.framework.common.Propiedades;
import ar.gov.posadas.mbe.framework.common.Tools;
import ar.gov.posadas.mbe.ports.apirest.RestService;
import ar.gov.posadas.mbe.ports.filemanager.FilemanagerRequest;
import ar.gov.posadas.mbe.ports.filemanager.FilemanagerResponse;
import ar.gov.posadas.mbe.ports.filemanager.IFilemanagerOnActionClient;
import ar.gov.posadas.mbe.ports.filemanager.IFilemanagerOnActionServer;
import ar.gov.posadas.mbe.ports.filemanager.FilemanagerRequest.EFilemanagerAction;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@SuppressWarnings("null")
public class FilemanagerService implements IFilemanagerService, IFilemanagerOnActionServer {
    private final List<IFilemanagerOnActionClient> fmclients = new ArrayList<>();
    private final Map<EFilemanagerAction, Function<FilemanagerRequest, FilemanagerResponse>> actions;
    private final String entorno;
    private final String baseUrl;
    private final String urlFilemanagerActionUpload;
    private final String urlFilemanagerActionDelete;
    private final String urlFilemanagerActionRename;
    private final String urlFilemanagerActionVerifyOrDownload;
    private final String urlFilemanagerActionGetfiles;

    public FilemanagerService(Propiedades systemprops) {
        this.entorno = systemprops.getEntorno();
        this.baseUrl = systemprops.getFileman().get("baseurl");
        this.urlFilemanagerActionUpload = systemprops.getFileman().get("upload");
        this.urlFilemanagerActionDelete = systemprops.getFileman().get("delete");
        this.urlFilemanagerActionVerifyOrDownload = systemprops.getFileman().get("exist");
        this.urlFilemanagerActionRename = systemprops.getFileman().get("rename");
        this.urlFilemanagerActionGetfiles = systemprops.getFileman().get("getfiles");
        this.actions = Map.ofEntries(
            Map.entry(EFilemanagerAction.UPLOAD, this::subirArchivoAlRepositorio),
            Map.entry(EFilemanagerAction.DELETE, this::borrarArchivoDelRepositorio),
            Map.entry(EFilemanagerAction.RENAME, this::renombrarArchivoDelRepositorio),
            Map.entry(EFilemanagerAction.VERIFY, this::verificarArchivoDelRepositorio),
            Map.entry(EFilemanagerAction.DOWNLOAD, this::obtenerArchivoDelRepositorio),
            Map.entry(EFilemanagerAction.LIST, this::listarArchivosDelRepositorio));
    }

    /**
     * Metodo para aplicar el observer y agregar un cliente al servicio de filemanager
     */
    @Override
    public void addClient(IFilemanagerOnActionClient client) {
        if (client != null) {
            fmclients.add(client);
        }
    }

    /**
     * Servicio para ejecutar una accion sobre un archivo en el repositorio
     * NOTA: requiere obligatoriamente encontrar un IFileManagerClient que ejecute
     * <p>
     * Los Tipos "contextos" Posibles son (a hoy):
     * persona
     * usuario
     * agente
     * tramite
     * dispositivo
     * patrimonio
     * <p>
     * Los Subtipos de "contextos" Posibles son (a hoy y definidos en EFilemanagerSubtipo):
     * foto
     * dni
     * doc
     * - "nombrepropuesto" indica con que nombre se debera renombar en el repositorio
     * al archivo que es enviado para su grabacion. Siempre se respeta la extension
     * del archivo enviado
     * - "creasubcartepa" envia false o true de acuerdo a si se debera crear una carpeta
     * contenedora para el archivo enviado, esta carpeta sera una subcarpeta de la que
     * tiene el mismo nombre que tipo + "/" + subtipo
     * - "nombresubcarpeta" es el nombre de la carpeta a considerar segun la necesidad
     * descripta mas arriba
     * - "id" es el id de la tabla requerida para actualizar datos en algunos casos.
     * por ejemplo en tipo=persona o tipo=usuario se graba el nombre de la imagen
     * subida al repositorio.
     * NOTA: requiere obligatoriamente encontrar un IFileManagerClient que audite
     */
    public FilemanagerResponse ejecutarFileActionForRequest(FilemanagerRequest fmreq) {
        if (!isOkDataForRequest(fmreq)) {
            return FilemanagerResponse.noDataForRequest(fmreq);
        }

        // obtiene la clase cliente relacionada al requerimiento
        // (aqui el patron observer se modifica para que solamente
        // un cliente pueda atender a un determinado requerimiento)
        var fmclient = this.getFilemanagerClient(fmreq);
        if (!fmclient.isPresent()) {
            return FilemanagerResponse.noServiceClient(fmreq);
        }

        // ejecuta las acciones previas requeridas de la accion
        var fmresponse = fmclient.get().filemanagerPreAction(fmreq);
        if (!fmresponse.ok()) {
            return fmresponse;
        }

        // actualiza el requerimieto por si el cliente actualizó
        // algo al ejecutar acciones previas sobre el requerimiento
        fmreq = fmresponse.request();

        // ejecuta la accion y verifica si la respuesta tiene un
        // estado http y si el mismo puede interpretarse como valido
        var response = actions.get(fmreq.action()).apply(fmreq);
        if (!HttpStatus.valueOf(response.status()).is2xxSuccessful()) {
            return FilemanagerResponse.fatal(fmreq);
        }

        // ejecuta la postcondicion de la accion pasando la respuesta
        fmclient.get().filemanagerPosAction(response);

        // finalmente arma y devuelve la respuesta estilo filemanager
        return response;
    }

    /**
     * Servicio Para Subir Archivos al Repositorio
     */
    private FilemanagerResponse subirArchivoAlRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.post(getURLForHTTPAction(urlFilemanagerActionUpload, fmreq),
                                            MediaType.MULTIPART_FORM_DATA,
                                            null,
                                            null,
                                            FileserverResponse.class,
                                            null,
                                            fmreq.file());
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getBody().ok(),
                                              restResponse.getStatusCode().value(),
                                              restResponse.getBody().msg(),
                                              restResponse.getBody().nombreArchivo(),
                                              null,
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    /**
     * Servicio Para Borrar Archivos del Repositorio
     */
    private FilemanagerResponse borrarArchivoDelRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.post(getURLForHTTPAction(urlFilemanagerActionDelete, fmreq),
                                            MediaType.MULTIPART_FORM_DATA,
                                            null,
                                            null,
                                            FileserverResponse.class,
                                            null,
                                            null);
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getBody().ok(),
                                              restResponse.getStatusCode().value(),
                                              restResponse.getBody().msg(),
                                              restResponse.getBody().nombreArchivo(),
                                              null,
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    /**
     * Servicio para Renombrar un Archivo especifico del Repositorio
     */
    private FilemanagerResponse renombrarArchivoDelRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.post(getURLForHTTPAction(urlFilemanagerActionRename, fmreq),
                                            MediaType.MULTIPART_FORM_DATA,
                                            null,
                                            null,
                                            FileserverResponse.class,
                                            null,
                                            null);
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getBody().ok(),
                                              restResponse.getStatusCode().value(),
                                              restResponse.getBody().msg(),
                                              restResponse.getBody().nombreArchivo(),
                                              null,
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    /**
     * Servicio para Verificar la Existencia de un archivo especifico en el Repositorio
     */
    private FilemanagerResponse verificarArchivoDelRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.exchange(getURLForHTTPAction(urlFilemanagerActionVerifyOrDownload, fmreq),
                                                HttpMethod.GET,
                                                MediaType.MULTIPART_FORM_DATA,
                                                null,
                                                null,
                                                null,
                                                FileserverResponse.class,
                                                null);
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getBody().ok(),
                                              restResponse.getStatusCode().value(),
                                              restResponse.getBody().msg(),
                                              restResponse.getBody().nombreArchivo(),
                                              null,
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    /**
     * Servicio para Obtener un Archivo especifico del Repositorio
     */
    private FilemanagerResponse obtenerArchivoDelRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.exchange(getURLForHTTPAction(urlFilemanagerActionVerifyOrDownload, fmreq),
                                                HttpMethod.GET,
                                                MediaType.MULTIPART_FORM_DATA,
                                                null,
                                                null,
                                                List.of(MediaType.APPLICATION_OCTET_STREAM),
                                                byte[].class,
                                                null);
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getStatusCode().is2xxSuccessful(),
                                              restResponse.getStatusCode().value(),
                                              Constantes.MSJ_INF_FILE_OK,
                                              fmreq.filename(),
                                              restResponse.getBody(),
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    /**
     * Servicio para Obtener la Lista de Archivos de una carpeta en el Repositorio
     */
    private FilemanagerResponse listarArchivosDelRepositorio(FilemanagerRequest fmreq) {
        var restResponse = RestService.exchange(getURLForHTTPAction(urlFilemanagerActionGetfiles, fmreq),
                                                HttpMethod.GET,
                                                MediaType.MULTIPART_FORM_DATA,
                                                null,
                                                null,
                                                null,
                                                String.class,
                                                null);
        return (restResponse != null && restResponse.hasBody())
                    ? new FilemanagerResponse(restResponse.getStatusCode().is2xxSuccessful(),
                                              restResponse.getStatusCode().value(),
                                              restResponse.getBody(),
                                              fmreq.filename(),
                                              null,
                                              fmreq)
                    : FilemanagerResponse.noDataForRequest(fmreq);
    }

    private Optional<IFilemanagerOnActionClient> getFilemanagerClient(FilemanagerRequest fmreq) {
        return this.fmclients.stream()
                             .filter(c -> c.isFilemanagerClientForRequest(fmreq))
                             .findFirst();
    }

    private boolean isOkDataForRequest(FilemanagerRequest fmreq) {
        return (StringUtils.hasText(fmreq.tipo())
                && fmreq.subtipo() != null)
                && !StringUtils.hasText(fmreq.filename())
                && (!fmreq.opsubcarpeta() || StringUtils.hasText(fmreq.subcarpeta()));
    }

    private String obtenerTokenParaRepositorio() {
        return Tools.getMd5Token(Fechas.getNowDateAsString("d/m/y"));
    }

    private String getURLForHTTPAction(String urlAccion, FilemanagerRequest fmreq) {
        // Tener en cuenta que el nombre propuesto no tiene
        // extension => se respeta la del archivo original.
        String url = baseUrl
                     + urlAccion
                     + "/"
                     + obtenerTokenParaRepositorio()
                     + entorno
                     + "/";

        if (urlAccion.equals(urlFilemanagerActionUpload)) {
            url += fmreq.tipo().toLowerCase()
                    + "/"
                    + fmreq.subtipo().name().toLowerCase()
                    + "/"
                    + fmreq.filename().toLowerCase()
                    + "/"
                    + fmreq.opsubcarpeta()
                    + "/"
                    + fmreq.subcarpeta().toLowerCase()
                    + "/"
                    + "true"//multipart
                    + "/"
                    + fmreq.id().toString();
        } else if (urlAccion.equals(urlFilemanagerActionDelete)) {
            url += fmreq.tipo().toLowerCase()
                    + "/"
                    + fmreq.opsubcarpeta()
                    + "/"
                    + fmreq.subcarpeta().toLowerCase()
                    + "/"
                    + fmreq.filename().toLowerCase();
        } else if (urlAccion.equals(urlFilemanagerActionVerifyOrDownload)) {
            url += fmreq.subcarpeta().toLowerCase()
                    + "/"
                    + fmreq.filename().toLowerCase();
        } else if (urlAccion.equals(urlFilemanagerActionRename)) {
            url += fmreq.subcarpeta().toLowerCase()
                   + "/"
                   + fmreq.filename().toLowerCase()
                   + "/"
                   + fmreq.extra().toLowerCase();
        } else if (urlAccion.equals(urlFilemanagerActionGetfiles)) {
            url += fmreq.tipo().toLowerCase()
                   + "/"
                   + fmreq.subtipo().name().toLowerCase()
                   + "/"
                   + fmreq.subcarpeta().toLowerCase();
        }

        return url;
    }
}
