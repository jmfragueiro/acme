package ar.com.acme.bootstrap.framework.jws;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.token.IEntityToken;
import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.common.Properties;
import ar.com.acme.bootstrap.framework.exception.AuthException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwsService implements IJwsService {
    private final String signingKey;
    private final String realm;

    public JwsService(Properties properties) {
        this.signingKey = properties.getSecurity().get("signing_key");
        this.realm = properties.getSecurity().get("realm");
    }

    @Override
    public String generateJws(IEntityToken source) {
        return Jwts.builder()
                      .header()
                        .keyId(this.realm)
                        .and()
                      .id(UUID.randomUUID().toString())
                      .subject(source.getName())
                      .issuedAt(java.sql.Date.valueOf(LocalDate.now()))
                      .signWith(getSecretKey())
                      .compact();
    }

    @Override
    public void validateJws(String jws) {
        try {
            if (jws == null || getBody(jws).isEmpty()) {
                throw new AuthException(Constants.MSJ_TOK_ERR_BADTOKEN, Constants.MSJ_TOK_ERR_EMPTYCLAIM);
            }

            if (!getKeyId(jws).equals(this.realm)) {
                throw new AuthException(Constants.MSJ_TOK_ERR_BADJWTSIGN, Constants.MSJ_TOK_ERR_BADJWT);
            }
        } catch (ExpiredJwtException e) {
            // ACA NO PASA NADA, SE RESUELVE MAS ADELANTE...
        } catch (SignatureException e) {
            throw new AuthException(Constants.MSJ_TOK_ERR_BADJWTSIGN, e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthException(Constants.MSJ_TOK_ERR_BADTOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthException(Constants.MSJ_TOK_ERR_TOKENNOTSUP, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthException(Constants.MSJ_TOK_ERR_EMPTYCLAIM, e.getMessage());
        } catch (Exception e) {
            throw new AuthException(Constants.MSJ_TOK_ERR_TOKENREINIT, e.getMessage());
        }
    }

    @Override
    public String getIdFromJws(String jws) {
        return getBody(jws).getId();
    }

    @Override
    public String getNameFromJws(String jws) {
        return getBody(jws).getSubject();
    }

    private Claims getBody(String jws) {
        return getParsedJws(jws).getPayload();
    }

    private String getKeyId(String jws) {
        return getParsedJws(jws).getHeader().getKeyId();
    }

    private Jws<Claims> getParsedJws(String jws) {
        return Jwts.parser()
                   .verifyWith(getSecretKey())
                   .build()
                   .parseSignedClaims(jws);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }
}
