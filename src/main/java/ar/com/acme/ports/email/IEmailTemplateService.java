package ar.com.acme.ports.email;

public interface IEmailTemplateService {
    String completeEmailText(String message, Boolean controlLectura, String token);

    String completarAsunto(String asunto);
}
