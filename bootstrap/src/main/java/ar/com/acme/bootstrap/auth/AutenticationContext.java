package ar.com.acme.bootstrap.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AutenticationContext {
    public static void setContextAuthentication(Authentication auth) {
        clearContextAuthentication();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void clearContextAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
