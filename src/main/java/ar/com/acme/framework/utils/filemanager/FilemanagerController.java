package ar.com.acme.framework.utils.filemanager;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ar.com.acme.framework.common.Constantes;
import ar.com.acme.ports.control.CtrlResponse;
import ar.com.acme.ports.filemanager.FilemanagerRequest;
import ar.com.acme.ports.filemanager.FilemanagerResponse;
import ar.com.acme.ports.filemanager.FilemanagerRequest.EFilemanagerAction;
import ar.com.acme.ports.filemanager.FilemanagerRequest.EFilemanagerSubtipo;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Constantes.SYS_CAD_FILEMAN_URL)
@RequiredArgsConstructor
public class FilemanagerController {
    private final IFilemanagerService fmService;

    /**
     * Metodo para Cargar Archivos al Repositorio
     */
    @PostMapping("upload/{tipo}/{subtipo}/{nombrepropuesto}/{creasubcarpeta}/{nombresubcarpeta}/{id}")
    public CtrlResponse<FilemanagerResponse> upload(@RequestParam("fileupload") MultipartFile file,
                                                    @PathVariable String tipo,
                                                    @PathVariable String subtipo,
                                                    @PathVariable String nombrepropuesto,
                                                    @PathVariable boolean creasubcarpeta,
                                                    @PathVariable String nombresubcarpeta,
                                                    @PathVariable("id") Long id) {
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.UPLOAD,
                                                              file,
                                                              tipo,
                                                              EFilemanagerSubtipo.valueOf(subtipo.trim().toUpperCase()),
                                                              nombrepropuesto.toLowerCase(),
                                                              nombresubcarpeta.toLowerCase(),
                                                              creasubcarpeta,
                                                              id,
                                                              null));
        return CtrlResponse.of(response.status(), response);
    }

    /**
     * Metodo para Verificar la existencia de un archivo en el repo
     */
    @GetMapping("verificarexistencia/{carpeta}/{archivo}")
    public CtrlResponse<FilemanagerResponse> verificaExistencia(@PathVariable String carpeta, @PathVariable String archivo) {
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.VERIFY,
                                                              null,
                                                              null,
                                                              null,
                                                              archivo,
                                                              carpeta,
                                                              false,
                                                              null,
                                                              null));
        return CtrlResponse.of(response.status(), response);
    }

    /**
     * Metodo para obtener el contenido de una carpeta del repositorio
     */
    @GetMapping("contenido/{tipo}/{subtipo}/{carpeta}")
    public CtrlResponse<String> listaDeArchivos(@PathVariable String tipo, @PathVariable String subtipo, @PathVariable String carpeta) {
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.LIST,
                                                              null,
                                                              tipo,
                                                              EFilemanagerSubtipo.valueOf(subtipo.trim().toUpperCase()),
                                                              null,
                                                              carpeta,
                                                              false,
                                                              null,
                                                              null));
        return CtrlResponse.of(response.status(), response.msg()); //aqui viene la lista de archivos en este caso
    }

    /**
     * Metodo para Renombrar Archivos del Repositorio
     */
    @GetMapping("rename/{carpeta}/{archivo}/{nombrenuevo}")
    public CtrlResponse<FilemanagerResponse> rename(@PathVariable String carpeta, @PathVariable String archivo, @PathVariable String nombrenuevo) {
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.RENAME,
                                                              null,
                                                              null,
                                                              null,
                                                              archivo,
                                                              carpeta,
                                                              false,
                                                              null,
                                                              nombrenuevo));
        return CtrlResponse.of(response.status(), response);
    }

    /**
     * Metodo para Descargar Archivos desde el Repósitorio
     */
    @GetMapping("download/{carpeta}/{archivo}/{tipo}/{subtipo}/{id}")
    public CtrlResponse<byte[]> download(@PathVariable String carpeta,
                                         @PathVariable String archivo,
                                         @PathVariable String tipo,
                                         @PathVariable String subtipo,
                                         @PathVariable Long id) {
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.DOWNLOAD,
                                                              null,
                                                              tipo,
                                                              EFilemanagerSubtipo.valueOf(subtipo.trim().toUpperCase()),
                                                              archivo,
                                                              carpeta,
                                                              false,
                                                              id,
                                                              null));
        return CtrlResponse.of(response.status(), response.file());
    }

    /**
     * Metodo para Borrar Archivos del Repósitorio
     */
    @PostMapping("delete/{tipo}/{subtipo}/{entrarcarpeta}/{subcarpeta}/{archivo}/{id}")
    public CtrlResponse<FilemanagerResponse> delete(@PathVariable String tipo,
                                                      @PathVariable String subtipo,
                                                      @PathVariable boolean entrarcarpeta,
                                                      @PathVariable String subcarpeta,
                                                      @PathVariable String archivo,
                                                      @PathVariable String id) {
        // OJO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // La variable id, recibida en los paramentros esta declarada como STRING
        // No cambiar esto, ya que en algunos casos se recibirá "null" como parámetro
        // y al leer @PathVariable String id interpreta null como un string y no como
        // una condicion de nulo. Se hace esta aclaracion porque después se usa esta
        // variable id transformandola a Long y la logica seria haber definido como
        // Long desde el principio.
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        var response = fmService.ejecutarFileActionForRequest(new FilemanagerRequest(EFilemanagerAction.DELETE,
                                                              null,
                                                              tipo,
                                                              EFilemanagerSubtipo.valueOf(subtipo.trim().toUpperCase()),
                                                              archivo,
                                                              subcarpeta,
                                                              entrarcarpeta,
                                                              id.equals("null") ? -1L : Long.parseLong(id),
                                                              null));
        return CtrlResponse.of(response.status(), response);
    }
}
