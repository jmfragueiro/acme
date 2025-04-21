package ar.com.acme.impldefault;

import org.springframework.stereotype.Service;

import ar.com.acme.framework.core.extradata.IReqScopeExtraDataService;
import ar.com.acme.framework.core.extradata.ReqScopeExtraData;
import ar.com.acme.framework.core.http.IHttpRequestAuthorizationValueDecoder;
import ar.com.acme.framework.core.jws.IJwsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultReqScopeExtraDataService implements IReqScopeExtraDataService {
    private final ReqScopeExtraData reqScopeExtraData;
    private final IJwsService jwsService;
    private final IHttpRequestAuthorizationValueDecoder httpAuthorizationValueDecoder;

    @Override
    public void setScopeExtraDataFromRequest(HttpServletRequest request) {
        reqScopeExtraData.set(jwsService.getExtraDataFromJws(httpAuthorizationValueDecoder.getValidAuthorizationValueFromRequest(request)));
    }
}
