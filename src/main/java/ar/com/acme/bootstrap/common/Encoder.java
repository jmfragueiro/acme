package ar.com.acme.bootstrap.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class Encoder {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(CharSequence rawpass) {
        return Encoder.passwordEncoder.encode(rawpass);
    }

    public static boolean passwordsMatch(String pass1, String pass2) {
        return Encoder.passwordEncoder.matches(pass1, pass2);
    }
}
