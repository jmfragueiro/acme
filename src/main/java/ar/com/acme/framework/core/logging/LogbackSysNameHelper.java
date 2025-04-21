package ar.gov.posadas.mbe.framework.core.logging;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogbackSysNameHelper extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return "MINIBACKEND";
    }
}
