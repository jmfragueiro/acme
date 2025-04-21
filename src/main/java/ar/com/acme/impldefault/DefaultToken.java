package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Fechas;
import ar.com.acme.framework.common.Tools;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.token.AbstractToken;
import ar.com.acme.framework.core.token.ITokenPayload;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultToken extends AbstractToken<String, ITokenPayload> {
    public DefaultToken(ITokenPayload objeto) {
        super(objeto);
    }

    @Override
    public String getId() {
        // aqui se utiliza, como identificador, al nombre de usuario
        // mas el datetime de creación del token, para asi segurar la
        // ´sincronizacion´ es decir que luego no se permita utilizar
        // un token viejo del mismo usuario
        return Tools.getSha256Token(
                getPayload().getName()
                            .concat(Constantes.SYS_CAD_CLOSEREF)
                            .concat(Fechas.formatLDT(getIssuedAt())));
    }

    @Override
    public String getSubject() {
        return getPayload().getName();
    }

    @Override
    public DefaultToken getTokenIfUseful() {
        getPayload().verifyCanOperate();
        return this;
    }

    @Override
    public List<String> authClaim() {
        try {
            return getPayload().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } catch (Exception e) {
            throw new AuthException(Constantes.MSJ_SES_ERR_NOGRANTS, e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getId();
    }
}
