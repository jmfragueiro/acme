package ar.gov.posadas.mbe.ports.filemanager;

import org.springframework.web.multipart.MultipartFile;

public record FilemanagerRequest(EFilemanagerAction action,
                                 MultipartFile file,
                                 String tipo,
                                 EFilemanagerSubtipo subtipo,
                                 String filename,
                                 String subcarpeta,
                                 boolean opsubcarpeta,
                                 Long id,
                                 String extra) {
    public enum EFilemanagerAction {
        UPLOAD,
        VERIFY,
        DOWNLOAD,
        LIST,
        DELETE,
        RENAME
    }

    public enum EFilemanagerSubtipo {
        FOTO,
        DNI,
        DOC,
        OTROS;
    }
}
