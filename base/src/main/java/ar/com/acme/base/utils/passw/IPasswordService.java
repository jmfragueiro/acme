package ar.com.acme.base.utils.passw;

public interface IPasswordService {
    Boolean isValidPassword(String rawPassword);

    String encode(CharSequence rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
