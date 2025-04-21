package ar.gov.posadas.mbe.framework.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Esta clase debe ser utilizada como un punto focal para todas los metodos genericos para trabajo con numeros
 * del framework, de manera de tener encasulada, en una sola clase, todas las cuestiones asociadas a este
 * tipo de necesidades.
 *
 * @author jmfragueiro
 * @version 20200201
 */
public abstract class Numeros {
    public static String format(Double valor, String format) {
        NumberFormat format1 = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat formater = (DecimalFormat) format1;
        formater.applyPattern(format);
        return formater.format(valor);
    }

    public static double round(double valor, int dec) {
        if (dec < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(dec, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    public static boolean esNumerico(String valor) {
        return isNumeric(valor);
    }

    public static String formatoParaImporte(Double importe) {
        return format(importe, "#,##0.00");
    }

    public static String formatoParaNumeroEntero(int documento) {
        return format((double) documento, "#,##0").replace(',', '.');
    }

    public static String formatoParaNumeroDocumento(int documento) {
        return format((double) documento, "#,##0").replace(',', '.');
    }

    public static String formatoParaNumeroCuit(Long cuit) {

        String cuit_ = cuit.toString();

        return cuit_.substring(0, 2) + "-" + cuit_.substring(2, 10) + "-" + cuit_.substring(10, 11);
    }
}
