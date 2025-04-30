package ar.com.acme.application.email;

import java.util.function.Predicate;

import ar.com.acme.application.common.AppProperties;

public class EmailService implements IEmailService {
    private final Predicate<String> isValidEmail;

        public EmailService(AppProperties appProperties) {
        this.isValidEmail = email -> { return email.matches(appProperties.getRegexp().get("email")); };
    }

    public Boolean isValidEmail(String email) {
        return isValidEmail.test(email);
    }
}
