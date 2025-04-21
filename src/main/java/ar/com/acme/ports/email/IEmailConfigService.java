package ar.gov.posadas.mbe.ports.email;

public interface IEmailConfigService {
    EmailProperties completeEmailPropertiesForSendDirId(Long dirsendId);
}
