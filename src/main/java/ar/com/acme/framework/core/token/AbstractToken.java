package ar.gov.posadas.mbe.framework.core.token;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import ar.gov.posadas.mbe.framework.common.Fechas;

public abstract class AbstractToken<K, U extends ITokenPayload> implements IToken<K, U> {
    private final Long min_interval = 60L;
    private final U payload;
    private final LocalDateTime issuedAt = LocalDateTime.now();
    private LocalDateTime expiration;

    public AbstractToken(U payload) {
        this.payload = payload;
        this.expiration = this.nextTerm(issuedAt, min_interval);
        payload.reinitAuthorities();
    }

    @Override
    public U getPayload() {
        return payload;
    }

    @Override
    public IToken<K, U> reinitTerm(Long interval) {
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
}
