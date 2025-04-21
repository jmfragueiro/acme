package ar.gov.posadas.mbe.impldefault;

import org.springframework.stereotype.Service;

import ar.gov.posadas.mbe.framework.core.extradata.IReqScopeExtraDataService;
import ar.gov.posadas.mbe.framework.core.extradata.ReqScopeExtraData;
import ar.gov.posadas.mbe.framework.core.http.IHttpRequestAuthorizationValueDecoder;
import ar.gov.posadas.mbe.framework.core.jws.IJwsService;
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
