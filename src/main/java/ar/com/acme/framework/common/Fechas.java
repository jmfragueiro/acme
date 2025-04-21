package ar.com.acme.framework.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Esta clase debe ser utilizada como un punto focal para todas los metodos genericos de trabajo con fechas
 * del sistema, de manera de tener encasulada, en una sola clase, todas las cuestiones asociadas a estas.
 * 
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Fechas {
    public static LocalDate sumarRestarDias(LocalDate fecha, Long dias) {

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date fecha1 = Date.from(fecha.atStartOfDay(defaultZoneId).toInstant());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha1);
        cal.add(Calendar.DATE, Math.toIntExact(dias));

        return cal.getTime().toInstant().atZone(defaultZoneId).toLocalDate();
    }

    public static String sumarHoras(String hora, Integer cantidad) {
        /**
         * Este metodo supone que la hora viene cargada en formato HH:MM
         */

        String horanueva = null;

        Integer horainicio = Integer.valueOf(hora.split(":")[0]);
        Integer minutoinicio = Integer.valueOf(hora.split(":")[1]);

        for (int i = 1; i <= cantidad; i++) {
            horainicio++;
            if (horainicio.equals(24)) {
                horainicio = 0;
            }
        }

        horanueva = String.format("%02d", horainicio) + ":" + String.format("%02d", minutoinicio);

        return horanueva;
    }

    public static Date toDate(LocalDate localdate) {

        return ((localdate != null) ? java.sql.Date.valueOf(localdate) : null);
    }

    public static Date toDate(LocalDateTime localdate) {

        return ((localdate != null) ? java.sql.Date.valueOf(localdate.toLocalDate()) : null);
    }

    public static LocalDate toLocalDate(Date date) {

        return ((date != null) ? LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date)) : null);
    }

    public static Date truncateDate(Date date) {

        return toDate(toLocalDate(date));
    }

    public static Date inicioMes(Date fecha) {
        fecha = truncateDate(fecha);
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date finMes(Date fecha) {
        fecha = truncateDate(fecha);
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static String getNowDateAsString(String formato) {
        String mifecha = "";

        if (formato.equals("")) {
            mifecha = (String.valueOf(String.format("%04d", Calendar.getInstance().get(Calendar.YEAR))) + String
                    .valueOf(String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1)) + String
                    .valueOf(String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))) + String
                    .valueOf(String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))) + String
                    .valueOf(String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))) + String
                    .valueOf(String.format("%02d", Calendar.getInstance().get(Calendar.SECOND))));
        } else if (formato.equals("d/m/y")) {
            mifecha = (String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) +
                    "/" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1) +
                    "/" +
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        } else if (formato.equals("yyyy-MM-dd HH:mm:ss")) {
            mifecha = String.format("%04d", Calendar.getInstance().get(Calendar.YEAR))
                    + "-" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1)
                    + "-" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    + " " +
                    String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                    + ":" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))
                    + ":" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.SECOND));
        } else if (formato.equals("dd-MM-yyyy HH:mm:ss")) {
            mifecha = String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    + "-" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1)
                    + "-" +
                    String.format("%04d", Calendar.getInstance().get(Calendar.YEAR))
                    + " " +
                    String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                    + ":" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))
                    + ":" +
                    String.format("%02d", Calendar.getInstance().get(Calendar.SECOND));
        } else if (formato.equals("dd-MM-yyyy HH:mm:ss.SSS")) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            mifecha = sdf2.format(timestamp);
        }

        return mifecha;
    }

    public static Date fromString(String source, String pattern) {

        return toDate(LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern)));
    }

    public static String format(Date fecha, String formato) {

        return new SimpleDateFormat(formato).format(fecha);
    }

    public static Integer getNumeroDiaSemana(LocalDate fecha) {

        return fecha.getDayOfWeek().getValue();
    }

    public static Integer getNumeroDiaSemanaPostgreSQL(LocalDate fecha) {

        return (fecha.getDayOfWeek().equals(DayOfWeek.SUNDAY)) ? 0 : fecha.getDayOfWeek().getValue();
    }

    public static List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);
        List<Date> listaFechas = new ArrayList<>();
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return listaFechas;
    }

    public static LocalDateTime fechaActualLocalDateTime() {

        return LocalDateTime.now();
    }

    public static Date fechaActualDate() {

        return Date.from(Instant.now());
    }

    public static LocalDate fechaActualLocalDate() {

        return LocalDate.now();
    }

    public static String horaActual() {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String horaActualCompleto() {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static Integer parteFechaActual(String parte) {
        int valor = 0;

        if (parte.equals("D")) {
            valor = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        if (parte.equals("M")) {
            valor = Calendar.getInstance().get(Calendar.MONTH);
        }
        if (parte.equals("Y")) {
            valor = Calendar.getInstance().get(Calendar.YEAR);
        }

        return valor;
    }

    public static Integer parteFechaTipoLocalDate(LocalDate fecha, String parte) {
        int valor = 0;

        if (parte.equals("D")) {
            valor = fecha.getDayOfMonth();
        }
        if (parte.equals("M")) {
            valor = fecha.getMonthValue();
        }
        if (parte.equals("Y")) {
            valor = fecha.getYear();
        }

        return valor;
    }

    public static Integer parteFechaTipoLocalDateTime(LocalDateTime fecha, String parte) {
        int valor = 0;

        if (parte.equals("D")) {
            valor = fecha.getDayOfMonth();
        }
        if (parte.equals("M")) {
            valor = fecha.getMonthValue();
        }
        if (parte.equals("Y")) {
            valor = fecha.getYear();
        }

        return valor;
    }

    public static String fechaEnLetras(LocalDate fecha) {

        Integer dia = fecha.getDayOfMonth();
        Integer mes = fecha.getMonthValue();
        Integer ano = fecha.getYear();

        String fechaletras = dia.toString() + " días del mes de " + obtenerNombreMes(mes) + " de " + ano.toString();

        return fechaletras;
    }

    public static String fechaEnLetrasForma2(LocalDate fecha) {

        Integer dia = fecha.getDayOfMonth();
        Integer mes = fecha.getMonthValue();
        Integer ano = fecha.getYear();

        String fechaletras = dia.toString() + " de " + obtenerNombreMes(mes) + " de " + ano.toString();

        return fechaletras;
    }

    public static String obtenerNombreMes(Integer valor) {
        String mes = switch (valor) {
            case 1 -> "Enero";
            case 2 -> "Febrero";
            case 3 -> "Marzo";
            case 4 -> "Abril";
            case 5 -> "Mayo";
            case 6 -> "Junio";
            case 7 -> "Julio";
            case 8 -> "Agosto";
            case 9 -> "Septiembre";
            case 10 -> "Octubre";
            case 11 -> "Noviembre";
            case 12 -> "Diciembre";
            default -> "Mes Desconocido";
        };

        return mes;
    }

    public static Boolean isBefore(LocalDateTime fecha) {
        return fecha.isBefore(LocalDateTime.now());
    }

    public static String formatLDT(LocalDateTime ldt) {
        return ldt.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static String formatShortNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String formatLongNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static Integer getNowDateComponent(String parte) {
        int valor = 0;

        if (parte.equals("D")) {
            valor = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        if (parte.equals("M")) {
            valor = Calendar.getInstance().get(Calendar.MONTH);
        }
        if (parte.equals("Y")) {
            valor = Calendar.getInstance().get(Calendar.YEAR);
        }

        return valor;
    }

    public static Integer getDateComponent(LocalDate fecha, String parte) {
        int valor = 0;

        if (parte.equals("D")) {
            valor = fecha.getDayOfMonth();
        }
        if (parte.equals("M")) {
            valor = fecha.getMonthValue();
        }
        if (parte.equals("Y")) {
            valor = fecha.getYear();
        }

        return valor;
    }

}
