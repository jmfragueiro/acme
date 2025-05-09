package ar.com.acme.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase debe ser utilizada como un wrapper para las operaciones de logging del sistema.
 *
 * @author jmfragueiro
 * @version 20250505
 */
public abstract class Logging {
    public static void info(Class<?> clazz, String mensaje) {
        getLogger(clazz).info(mensaje);
    }

    public static void error(Class<?> clazz, String mensaje) {
        getLogger(clazz).error(mensaje);
    }

    public static void warning(Class<?> clazz, String mensaje) {
        getLogger(clazz).warn(mensaje);
    }

    public static void debug(Class<?> clazz, String mensaje) {
        getLogger(clazz).debug(mensaje);
    }

    public static void info(Class<?> clazz, String mensaje, Object extra) {
        info(clazz, reduce(mensaje, extra));
    }

    public static void error(Class<?> clazz, String mensaje, Object extra) {
        error(clazz, reduce(mensaje, extra));
    }

    public static void warning(Class<?> clazz, String mensaje, Object extra) {
        warning(clazz, reduce(mensaje, extra));
    }

    public static void debug(Class<?> clazz, String mensaje, Object extra) {
        debug(clazz, reduce(mensaje, extra));
    }

    private static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    private static String reduce(String mensaje, Object extra) {
        return new StringBuilder(mensaje)
                        .append(Constants.SYS_CAD_SPACE)
                        .append(Constants.SYS_CAD_OPENTYPE)
                        .append(extra.toString())
                        .append(Constants.SYS_CAD_CLOSETPE)
                        .toString();
    }
}
