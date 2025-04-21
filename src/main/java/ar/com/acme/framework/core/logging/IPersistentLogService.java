package ar.gov.posadas.mbe.framework.core.logging;

public interface IPersistentLogService {
    void info(Long registro, String tipo, String seccion, String mensaje, String extra);

    void warning(Long registro, String tipo, String seccion, String mensaje, String extra);

    void error(Long registro, String tipo, String seccion, String mensaje, String extra);
}
