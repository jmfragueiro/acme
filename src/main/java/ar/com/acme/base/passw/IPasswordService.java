package ar.com.acme.base.passw;

public interface IPasswordService {
    String encode(CharSequence rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
