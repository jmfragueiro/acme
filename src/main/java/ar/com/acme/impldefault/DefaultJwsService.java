package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Fechas;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.jws.IJwsDataHelper;
import ar.com.acme.framework.core.jws.IJwsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

@Service
public class DefaultJwsService implements IJwsService {
    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.realm}")
    private String realm;

    @Override
    public String generateJws(IJwsDataHelper source) {
        return Jwts.builder()
                      .header()
                        .keyId(this.realm)
                        .and()
                      .id(source.getId().toString())
                      .subject(source.getSubject())
                      .issuedAt(Fechas.toDate(source.getIssuedAt()))
                      .claim("authorities", source.authClaim())
                      .signWith(getSecretKey())
                      .compact();
    }

    @Override
    public void validateJws(String jws) {
        try {
            if (jws == null || getBody(jws).isEmpty()) {
                throw new AuthException(Constantes.MSJ_TOK_ERR_BADTOKEN, Constantes.MSJ_TOK_ERR_EMPTYCLAIM);
            }

            if (!getKeyId(jws).equals(this.realm)) {
                throw new AuthException(Constantes.MSJ_TOK_ERR_BADJWTSIGN, Constantes.MSJ_TOK_ERR_BADJWT);
            }
        } catch (ExpiredJwtException e) {
            // ACA NO PASA NADA, SE RESUELVE MAS ADELANTE...
        } catch (SignatureException e) {
            throw new AuthException(Constantes.MSJ_TOK_ERR_BADJWTSIGN, e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthException(Constantes.MSJ_TOK_ERR_BADTOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthException(Constantes.MSJ_TOK_ERR_TOKENNOTSUP, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthException(Constantes.MSJ_TOK_ERR_EMPTYCLAIM, e.getMessage());
        } catch (Exception e) {
            throw new AuthException(Constantes.MSJ_TOK_ERR_TOKENREINIT, e.getMessage());
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
