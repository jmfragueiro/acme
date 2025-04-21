package ar.gov.posadas.mbe.framework.utils.filemanager;

import ar.gov.posadas.mbe.ports.filemanager.FilemanagerRequest;
import ar.gov.posadas.mbe.ports.filemanager.FilemanagerResponse;

public interface IFilemanagerService {
    FilemanagerResponse ejecutarFileActionForRequest(FilemanagerRequest fmreq);
}
