package ar.com.acme.application.password;

import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.acme.commons.Properties;

@Service
public class PasswordService implements IPasswordService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Predicate<String> isValidPassword;

    public PasswordService(Properties properties) {
        this.isValidPassword = password -> { return password.matches(properties.getRegexp().get("password")); };
    }

    ///////////////////////////////////////////////////////
    // PARA GENERAR LAS CLAVES INICIALES PARA PROBAR:    //
    // (hay que debuggear y parar en la captura de pass  //
    //  y guardar el pass generado en la base de datos)  //
    ///////////////////////////////////////////////////////
    // var passService = new PaswordService();           //
    // var pass = passService.encode("nuevo_password");  //
    ///////////////////////////////////////////////////////
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
