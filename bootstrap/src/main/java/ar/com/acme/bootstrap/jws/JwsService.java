package ar.com.acme.bootstrap.jws;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import ar.com.acme.adapter.principal.IPrincipal;
import ar.com.acme.commons.Constants;
import ar.com.acme.commons.Properties;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwsService implements IJwsService {
    private final String signingKey;
    private final String realm;

    public JwsService(Properties properties) {
        this.signingKey = properties.getJws().get("signing_key");
        this.realm = properties.getJws().get("realm");
    }

    @Override
    public String generateJws(IPrincipal source) {
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
                throw new JWSException(Constants.MSJ_TOK_ERR_BADTOKEN, Constants.MSJ_TOK_ERR_EMPTYCLAIM);
            }

            if (!getKeyId(jws).equals(this.realm)) {
                throw new JWSException(Constants.MSJ_TOK_ERR_BADJWTSIGN, Constants.MSJ_TOK_ERR_BADJWT);
            }
        } catch (ExpiredJwtException e) {
            // ACA NO PASA NADA, SE RESUELVE MAS ADELANTE...
        } catch (SignatureException e) {
            throw new JWSException(Constants.MSJ_TOK_ERR_BADJWTSIGN, e.getMessage());
        } catch (MalformedJwtException e) {
            throw new JWSException(Constants.MSJ_TOK_ERR_BADTOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JWSException(Constants.MSJ_TOK_ERR_TOKENNOTSUP, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JWSException(Constants.MSJ_TOK_ERR_EMPTYCLAIM, e.getMessage());
        } catch (Exception e) {
            throw new JWSException(Constants.MSJ_TOK_ERR_TOKENREINIT, e.getMessage());
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

    @Override
    public LocalDateTime getIatFromJws(String jws) {
        return LocalDateTime.ofInstant(getBody(jws).getIssuedAt().toInstant(), ZoneId.systemDefault());
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
