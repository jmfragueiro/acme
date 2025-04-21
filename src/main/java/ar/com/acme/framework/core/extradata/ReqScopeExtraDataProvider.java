package ar.com.acme.framework.core.extradata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ReqScopeExtraDataProvider {
    @Bean
    @RequestScope
    ReqScopeExtraData getRequestScopeED() {
        return new ReqScopeExtraData();
    }
}