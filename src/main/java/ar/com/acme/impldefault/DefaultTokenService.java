package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.exception.TokenException;
import ar.com.acme.framework.core.token.ITokenPrincipal;
import ar.com.acme.framework.core.token.ITokenService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultTokenService implements ITokenService<String, DefaultToken, DefaultTokenRepo> {
    private final DefaultTokenRepo tokenRepo;

    @Value("${security.token.validity}")
    private String validity;

    public DefaultTokenService(DefaultTokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public DefaultToken createToken(ITokenPrincipal source) {
        return new DefaultToken(source);
    }

    @Override
    public final DefaultToken initToken(DefaultToken token) {
        if (!tokenExists(token)) {
            tokenRepo.addToken(token.getId(), token);
        }
        token.reinitTerm(Long.parseLong(validity));
        return token;
    }

    @Override
    public final Optional<DefaultToken> getToken(String clave) {
        if (clave == null || clave.isBlank()) {
            throw new TokenException(Constantes.MSJ_SES_ERR_NOTOKENVALUEINFO);
        }

        return tokenRepo.getToken(clave);
    }

    @Override
    public boolean tokenExists(DefaultToken token) {
        return tokenRepo.tokenExist(token.getId());
    }

    @Override
    public void endToken(DefaultToken token) {
        tokenRepo.removeToken(token.getId());
    }

    @Override
    public DefaultTokenRepo getRepo() {
        return tokenRepo;
    }
}
