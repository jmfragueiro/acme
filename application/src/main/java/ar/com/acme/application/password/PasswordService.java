package ar.com.acme.application.password;

import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.acme.application.common.Properties;

@Service
public class PasswordService implements IPasswordService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Predicate<String> isValidPassword;

    public PasswordService(Properties properties) {
        this.isValidPassword = password -> { return password.matches(properties.getRegexp().get("password")); };
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
