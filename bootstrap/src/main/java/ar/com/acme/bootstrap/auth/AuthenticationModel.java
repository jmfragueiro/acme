package ar.com.acme.bootstrap.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.MessageException;
import jakarta.servlet.http.HttpServletRequest;

public record AuthenticationModel(String username, String password) {
    public static AuthenticationModel fromRequest(HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request.getReader(), AuthenticationModel.class);
        } catch (Exception ex) {
            throw new MessageException(Constants.MSJ_SES_ERR_ONAUTH, ex.getMessage());
        }
    }
}
