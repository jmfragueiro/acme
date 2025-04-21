package ar.com.acme.ports.email;

public interface IEmailConfigService {
    EmailProperties completeEmailPropertiesForSendDirId(Long dirsendId);
}
