package ar.com.acme.framework.utils.filemanager;

import ar.com.acme.ports.filemanager.FilemanagerRequest;
import ar.com.acme.ports.filemanager.FilemanagerResponse;

public interface IFilemanagerService {
    FilemanagerResponse ejecutarFileActionForRequest(FilemanagerRequest fmreq);
}
