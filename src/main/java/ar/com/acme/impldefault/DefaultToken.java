package ar.com.acme.impldefault;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Fechas;
import ar.com.acme.framework.common.Tools;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.jws.IJwsDataHelper;
import ar.com.acme.framework.core.token.IToken;
import ar.com.acme.framework.core.token.ITokenPayload;

public class DefaultToken implements IToken<String, ITokenPayload>, IJwsDataHelper {
    private final Long min_interval = 60L;
    private final ITokenPayload payload;
    private final LocalDateTime issuedAt = LocalDateTime.now();
    private LocalDateTime expiration;

    public DefaultToken(ITokenPayload payload) {
        this.payload = payload;
        this.expiration = this.nextTerm(issuedAt, min_interval);
        payload.reinitAuthorities();
    }

    @Override
    public ITokenPayload getPayload() {
        return payload;
    }

    @Override
    public DefaultToken reinitTerm(Long interval) {
        this.expiration = nextTerm(LocalDateTime.now(), interval);
        return this;
    }

    @Override
    public boolean isExpired() {
        return Fechas.isBefore(this.expiration);
    }

    @Override
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    private LocalDateTime nextTerm(LocalDateTime ini, Long interval) {
        return ini.plus(Math.max(min_interval, (interval == null ? min_interval : interval)), ChronoUnit.SECONDS);
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
    public DefaultToken getTokenIfIsUseful() {
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
