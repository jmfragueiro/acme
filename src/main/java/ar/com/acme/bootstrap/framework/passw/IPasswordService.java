package ar.com.acme.bootstrap.framework.passw;

public interface IPasswordService {
    boolean matches(String rawPassword, String encodedPassword);
}
