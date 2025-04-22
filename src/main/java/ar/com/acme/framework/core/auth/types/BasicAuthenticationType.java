package ar.com.acme.framework.core.auth.types;

import java.util.Base64;

import org.springframework.stereotype.Service;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.core.auth.IAuthenticationHelper;
import ar.com.acme.framework.core.exception.AuthException;
import ar.com.acme.framework.core.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

@Service(Constantes.SYS_CAD_HTTPAUTH_BASIC)
public class BasicAuthenticationType implements IAuthenticationType {
        @Override
        public TokenAuthentication authenticate(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            ///////////////////////////////////////////////////////////////////
            // PARA VER/GENERAR CUAL ES EL CODIGO QUE DEBE UTILIZARSE:       //
            // var coded = new String(                                       //
            //                    Base64.getEncoder()                        //
            //                          .encode(clientid.concat(":")         //
            //                          .concat(clientsecret)                //
            //                          .getBytes()));                       //
            ///////////////////////////////////////////////////////////////////
            String authUser = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
            String authSecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
            if (!(authUser.equals(authHelper.getClientid()) && authSecret.equals(authHelper.getClientsecret()))) {
                throw new AuthException(Constantes.MSJ_REQ_ERR_BADREQUESTVALUE);
            }

            var repoUser = authHelper.getPrincipalService()
                                               .findByName(username)
                                               .orElseThrow(() -> new AuthException(Constantes.MSJ_SES_ERR_BADCREDENTIAL));

            return new TokenAuthentication(repoUser, password);
        }
}
