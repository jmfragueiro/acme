package ar.com.acme.base.passw;

import org.springframework.stereotype.Service;

import ar.com.acme.base.common.Encoder;

@Service
public class PasswordService implements IPasswordService {
    

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return (rawPassword != null && Encoder.passwordsMatch(rawPassword, encodedPassword));
    }
}
