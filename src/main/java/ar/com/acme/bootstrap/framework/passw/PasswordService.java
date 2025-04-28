package ar.com.acme.bootstrap.framework.passw;

import org.springframework.stereotype.Service;

import ar.com.acme.bootstrap.common.Encoder;

@Service
public class PasswordService implements IPasswordService {
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return (rawPassword != null && Encoder.passwordsMatch(rawPassword, encodedPassword));
    }
}
