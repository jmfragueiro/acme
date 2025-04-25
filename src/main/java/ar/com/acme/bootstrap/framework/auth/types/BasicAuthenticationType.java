package ar.com.acme.bootstrap.framework.auth.types;

import java.util.Base64;

import org.springframework.stereotype.Service;

import ar.com.acme.bootstrap.common.Constants;
import ar.com.acme.bootstrap.framework.auth.IAuthenticationHelper;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;

@Service(Constants.SYS_CAD_HTTPAUTH_BASIC)
public class BasicAuthenticationType implements IAuthenticationType {
        @Override
        public TokenAuthentication generateAuthentication(HttpServletRequest request, IAuthenticationHelper authHelper, String authcad) {
            ////////////////////////////////////////////////////////////////////////
            // PARA VER/GENERAR CUAL ES EL CODIGO DE CLIENTE QUE DEBE USARSE:     //
            // var coded = new String(                                            //
            //                    Base64.getEncoder()                             //
            //                          .encode(authHelper                        //
            //                             .getClientid().concat(":")             //
            //                             .concat(authHelper.getClientsecret())  //
            //                             .getBytes()));                         //
            ////////////////////////////////////////////////////////////////////////
            String authUser = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
            String authSecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
            if (!(authUser.equals(authHelper.getClientid()) && authSecret.equals(authHelper.getClientsecret()))) {
                throw new AuthException(Constants.MSJ_REQ_ERR_BADREQUESTVALUE);
            }

            String username = request.getParameter(Constants.SYS_CAD_TXTLOGGIN_USER);
            String password = request.getParameter(Constants.SYS_CAD_TXTLOGGIN_PASS);

            if (username == null || password == null) {
                throw new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL);
            }

            var repoUser = authHelper.getPrincipalService()
                                     .findByName(username)
                                     .orElseThrow(() -> new AuthException(Constants.MSJ_SES_ERR_BADCREDENTIAL));

            var authorities = authHelper.getPrincipalService()
                                        .getAuthorities(repoUser);

            return new TokenAuthentication(repoUser, password, authorities);
        }
}
