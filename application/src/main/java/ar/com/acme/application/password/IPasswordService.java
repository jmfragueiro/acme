package ar.com.acme.application.password;

public interface IPasswordService {
    Boolean isValidPassword(String rawPassword);

    String encode(CharSequence rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
