package ar.com.acme.impldefault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.com.acme.framework.core.token.AbstractTokenRepo;

import java.util.Hashtable;
import java.util.Map;

@Component
public class DefaultTokenRepo extends AbstractTokenRepo<String, DefaultToken> {
    private final Long min_token_validity;

    public DefaultTokenRepo(@Value("${security.min_token_validity}") Long min_token_validity) {
        this.min_token_validity = min_token_validity;
    }

    @Override
    protected Map<String, DefaultToken> getRepo() {
        ////////////////////////////////////////////////////////////////////
        // se usa Hashtable para asegurar que no existan tokens sin clave //
        ////////////////////////////////////////////////////////////////////
        return new Hashtable<>();
    }

    @Override
    protected Long getMinTokenValidity() {
        return min_token_validity;
    }
}
