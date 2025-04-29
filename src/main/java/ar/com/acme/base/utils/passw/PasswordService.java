package ar.com.acme.base.utils.passw;

import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.acme.base.common.BaseProperties;

@Service
public class PasswordService implements IPasswordService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final Predicate<String> isValidPassword;

    public PasswordService(BCryptPasswordEncoder passwordEncoder, BaseProperties baseProperties) {
        this.passwordEncoder = passwordEncoder;
        this.isValidPassword = password -> { return password.matches(baseProperties.getRegexp().get("password")); };
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return (rawPassword != null && passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Override
    public Boolean isValidPassword(String rawPassword) {
        return isValidPassword.test(rawPassword);
    }
}
