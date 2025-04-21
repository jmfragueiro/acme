package ar.com.acme.impldefault;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Logging;
import ar.com.acme.framework.core.extradata.IExtraDataService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DefaultExtraDataService implements IExtraDataService {
    protected final ObjectMapper om = new ObjectMapper();

    public DefaultExtraDataService() {
        //om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        //om.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        //om.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Object create(Object origen) {
        return origen.getClass().toString();
    }

    @Override
    public Object extract(Object ted) {
        try {
            return om.readValue(ted.toString(), String.class);
        } catch(JsonProcessingException jpe) {
            Logging.info(this.getClass(), Constantes.MSJ_SEC_ERR_CANTEXTRACTTED);
            return null;
        }
    }
}
