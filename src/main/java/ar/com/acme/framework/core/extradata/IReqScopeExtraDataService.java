package ar.com.acme.framework.core.extradata;

import jakarta.servlet.http.HttpServletRequest;

public interface IReqScopeExtraDataService {
    void setScopeExtraDataFromRequest(HttpServletRequest request);
}