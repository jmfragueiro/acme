package ar.gov.posadas.mbe.ports.email;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public record EmailProperties(String host, Integer port, String username, String password, Properties properties) {
    public void applyTo(JavaMailSenderImpl sender) {
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setJavaMailProperties(properties);
    }
}
