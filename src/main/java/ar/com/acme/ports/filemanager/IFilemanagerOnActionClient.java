package ar.com.acme.ports.filemanager;

public interface IFilemanagerOnActionClient {
    boolean isFilemanagerClientForRequest(FilemanagerRequest request);

    default FilemanagerResponse filemanagerPreAction(FilemanagerRequest fmreq) {
        return new FilemanagerResponse(true, 200, null, fmreq.filename(), null, fmreq);
    }

    void filemanagerPosAction(FilemanagerResponse response);
}
