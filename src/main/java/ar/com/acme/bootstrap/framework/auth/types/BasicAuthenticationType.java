package ar.com.acme.bootstrap.framework.auth.types;

import java.util.Base64;

import org.springframework.stereotype.Service;

import ar.com.acme.base.principal.IEntityPrincipal;
import ar.com.acme.base.principal.IEntityPrincipalService;
import ar.com.acme.bootstrap.common.BootstrapConstants;
import ar.com.acme.bootstrap.common.BootstrapProperties;
import ar.com.acme.bootstrap.framework.exception.AuthException;
import ar.com.acme.bootstrap.framework.token.TokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service(BootstrapConstants.SYS_CAD_HTTPAUTH_BASIC)
@RequiredArgsConstructor
public class BasicAuthenticationType implements IAuthenticationType {
        private final IEntityPrincipalService<IEntityPrincipal> principalService;
        private final String clientid;
        private final String clientsecret;

        public BasicAuthenticationType(IEntityPrincipalService<IEntityPrincipal> principalService, BootstrapProperties properties) {
            this.principalService = principalService;
            this.clientid = properties.getSecurity().get("jwt_client-id");
            this.clientsecret = properties.getSecurity().get("jwt_client-secret");
        }

        @Override
        public TokenAuthentication generateAuthentication(HttpServletRequest request, String authcad) {
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

            return new TokenAuthentication(repoUser, password, authorities);
        }
}
