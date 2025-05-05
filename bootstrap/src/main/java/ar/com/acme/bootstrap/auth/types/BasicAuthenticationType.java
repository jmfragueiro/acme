package ar.com.acme.bootstrap.auth.types;

import java.util.Base64;
import org.springframework.stereotype.Service;

import ar.com.acme.application.principal.IPrincipal;
import ar.com.acme.application.principal.IPrincipalService;
import ar.com.acme.bootstrap.auth.AuthenticationToken;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.common.BootstrapProperties;
import ar.com.acme.bootstrap.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(BootstrapConstants.SYS_CAD_HTTPAUTH_BASIC)
@RequiredArgsConstructor
public class BasicAuthenticationType implements IAuthenticationType {
        private final IPrincipalService<? extends IPrincipal> principalService;
        private final BootstrapProperties properties;

        @Override
        public AuthenticationToken generateAuthentication(HttpServletRequest request, String authcad) {
            var clientid = properties.getSecurity().get("jwt_client-id");
            var clientsecret = properties.getSecurity().get("jwt_client-secret");

            ////////////////////////////////////////////////////////////////////////
            // PARA VER/GENERAR CUAL ES EL CODIGO DE CLIENTE QUE DEBE USARSE HAY  //
            // QUE DESCOMENTAR Y DEBUGEAR (VIA BREAKPOINT) EL CODIGO:             //
            // var coded = new String(                                            //
            //                    Base64.getEncoder()                             //
            //                          .encode(authHelper                        //
            //                             .getClientid().concat(":")             //
            //                             .concat(authHelper.getClientsecret())  //
            //                             .getBytes()));                         //
            ////////////////////////////////////////////////////////////////////////
            String authid = new String(Base64.getDecoder().decode(authcad)).split(":")[0];
            String authsecret = new String(Base64.getDecoder().decode(authcad)).split(":")[1];
            if (!(authid.equals(clientid) && authsecret.equals(clientsecret))) {
                throw new AuthException(BootstrapConstants.MSJ_REQ_ERR_BADREQUESTVALUE);
            }

            String username = request.getParameter(BootstrapConstants.SYS_CAD_TXTLOGGIN_USER);
            String password = request.getParameter(BootstrapConstants.SYS_CAD_TXTLOGGIN_PASS);
            if (username == null || password == null) {
                throw new AuthException(BootstrapConstants.MSJ_SES_ERR_BADCREDENTIAL);
            }

            var repoUser = principalService.findByName(username)
                                           .orElseThrow(() -> new AuthException(BootstrapConstants.MSJ_SES_ERR_BADCREDENTIAL));

            var authorities = principalService.getAuthorities(repoUser);

            return new AuthenticationToken(repoUser, password, authorities);
        }
}
