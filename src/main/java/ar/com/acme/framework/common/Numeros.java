package ar.com.acme.framework.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Esta clase debe ser utilizada como un punto focal para todas los metodos genericos de trabajo con numeros
 * del framework, de manera de tener encasulada, en una sola clase, todas las cuestiones asociadas a estos.
 *
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Numeros {
    public static String format(Double valor, String format) {
        NumberFormat format1 = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat formater = (DecimalFormat) format1;

        formater.applyPattern(format);

        return formater.format(valor);
    }

    public static double round(double valor, int dec) {
        if (dec < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(dec, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
