package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Fechas;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.extradata.IExtraDataService;
import ar.com.acme.framework.core.jws.IJwsDataHelper;
import ar.com.acme.framework.core.jws.IJwsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

@Service
public class DefaultJwsService implements IJwsService {
    private final IExtraDataService edService;

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.realm}")
    private String realm;

    public DefaultJwsService(List<IExtraDataService> edsList, @Value("${extradata.service}") String edsname) {
        edService = (edsList != null) ? edsList.stream()
                                               .filter(e -> e.getClass().getName().equals(edsname))
                                               .findFirst()
                                               .orElse(new DefaultExtraDataService()) : null;
    }

    @Override
    public String generateJws(IJwsDataHelper source) {
        var jws = Jwts.builder()
                      .header()
                        .keyId(this.realm)
                        .and()
                      .id(source.getId().toString())
                      .subject(source.getSubject())
                      .issuedAt(Fechas.toDate(source.getIssuedAt()))
                      .claim("authorities", source.authClaim());

        if (edService != null) {
            jws.claim("ted", edService.create(source));
        }

        return jws.signWith(getSecretKey()).compact();
    }

    @Override
    public void validateJws(String jws) {
        try {
            if (jws == null || getBody(jws).isEmpty()) {
                throw new AuthException(Constantes.MSJ_SEC_ERR_BADTOKEN, Constantes.MSJ_SEC_ERR_EMPTYCLAIM);
            }

            if (!getKeyId(jws).equals(this.realm)) {
                throw new AuthException(Constantes.MSJ_SEC_ERR_BADJWTSIGN, Constantes.MSJ_SEC_ERR_BADJWT);
            }
        } catch (ExpiredJwtException e) {
            // ACA NO PASA NADA, SE RESUELVE MAS ADELANTE...
        } catch (SignatureException e) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADJWTSIGN, e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_BADTOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_TOKENNOTSUP, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_EMPTYCLAIM, e.getMessage());
        } catch (Exception e) {
            throw new AuthException(Constantes.MSJ_SEC_ERR_TOKENREINIT, e.getMessage());
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
    public Object getExtraDataFromJws(String jws) {
        return (edService != null ? edService.extract(getBody(jws).get("ted")) : null);
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
