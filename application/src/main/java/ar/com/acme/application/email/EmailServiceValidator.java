package ar.com.acme.application.email;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import ar.com.acme.commons.Properties;

@Service
public class EmailServiceValidator implements IEmailServiceValidator {
    private final Predicate<String> isValidEmail;

        public EmailServiceValidator(Properties appProperties) {
        this.isValidEmail = email -> { return email.matches(appProperties.getRegexp().get("email")); };
    }

    public Boolean isValidEmail(String email) {
        return isValidEmail.test(email);
    }
}
