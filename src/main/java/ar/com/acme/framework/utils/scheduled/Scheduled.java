package ar.com.acme.framework.utils.scheduled;

import ar.com.acme.framework.common.Fechas;
import ar.com.acme.sistema.domiciliofiscal.notificacion.DfNotificacionProcesarEnvioService;
import ar.com.acme.sistema.evento.procesoventa.ProcesoenvioService;
import ar.com.acme.sistema.forenea.falta.procesos.ProcesarCausa;
import ar.com.acme.sistema.forenea.seccion.tiposeccion.Tiposeccion;
import ar.com.acme.sistema.libredeuda.log.LdILogService;
import ar.com.acme.sistema.libredeuda.procesoslibredeuda.ProcesarSolicitudService;
import ar.com.acme.sistema.libredeuda.registro.LdIRegistroService;
import ar.com.acme.sistema.libredeuda.registro.LdRegistro;
import ar.com.acme.sistema.notificacion.log.NaILogService;
import ar.com.acme.sistema.notificacion.notificacion.NaProcesarNotificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableScheduling
@SuppressWarnings("unused")
public class Scheduled {
    private static final String TIME_ZONE = "America/Buenos_Aires";

    private final long SEGUNDO = 1000;
    private final long SEGUNDO_5 = 1000 * 5;
    private final long SEGUNDO_10 = 1000 * 10;
    private final long SEGUNDO_15 = 1000 * 15;
    private final long SEGUNDO_30 = 1000 * 30;
    private final long SEGUNDO_45 = 1000 * 45;
    private final long SEGUNDO_55 = 1000 * 55;

    private final long MINUTO = SEGUNDO * 60;
    private final long MINUTO_2 = MINUTO * 2;
    private final long MINUTO_3 = MINUTO * 3;
    private final long MINUTO_5 = MINUTO * 5;
    private final long MINUTO_6 = MINUTO * 6;
    private final long MINUTO_10 = MINUTO * 10;
    private final long MINUTO_15 = MINUTO * 15;

    private final long HORA = MINUTO * 60;

    @Value("${server.entorno}")
    private String entornoEjecucion;

    @Autowired
    DfNotificacionProcesarEnvioService notificacionProcesarEnvioService;

    @Autowired
    ProcesoenvioService procesoenvioService;

    @Autowired
    ProcesarSolicitudService procesarSolicitudService;

    @Autowired
    LdIRegistroService registroService;

    @Autowired
    ProcesarCausa procesarCausa;

    @Autowired
    NaProcesarNotificacion procesarNotificacion;

    @Autowired
    LdILogService logService;

    @Autowired
    NaILogService nalogService;

    //    @Scheduled(cron = "0 0 12")

    /**
     * @return boolean Verifica la condicion de proceso segun el entorno en que se ejecutan los scheduler´s
     */
    private boolean condicionDeProceso() {
        boolean procesar = true;
        if (entornoEjecucion.equals("DESARROLLO")) {
            /* pongo en true o false de acuerdo a como quiero que quede DESARROLLO */
            procesar = true;
        } else if (entornoEjecucion.equals("PRE-PRODUCCION")) {
            /* pongo en true o false de acuerdo a como quiero que quede PRE-PRODUCCION */
            procesar = false;
        }

        return procesar;
    }

    /**
     * ************************************************************************************
     * ************************************************************************************
     * REFERIDOS AL MODULO DE DOMICILIO FISCAL
     * ************************************************************************************
     * ************************************************************************************
     */
    /* Esta Funcion Ejecuta en Envio de las Notificaciones Autorizadas en Domicilio Fiscal */
    @Async
    @org.springframework.scheduling.annotation.Scheduled(initialDelay = MINUTO, fixedDelay = MINUTO_15)
    public void procesarEnvioNotificacionAutorizadaDF() {
        notificacionProcesarEnvioService.procesarEnvioNotificacionDF();
    }

    /**
     * ************************************************************************************
     * ************************************************************************************
     * REFERIDOS AL MODULO DE SOLICITUD DE INFORMES DE DEUDA
     * ************************************************************************************
     * ************************************************************************************
     */
    /* Esta Funcion Verifica las Solicitudes de Informes, Verifica solicitudes iniciadas para PAGO y Solicitudes PAGADAS para envio de Informe */
    @Async
    @org.springframework.scheduling.annotation.Scheduled(initialDelay = SEGUNDO, fixedDelay = MINUTO_3)
    public void procesarSolicitudInformeDeuda() {
        String tipo = "INFO";
        String origen = "SCHEDULED";
        String tiposeccion = "";
        Optional<Tiposeccion> ts = Optional.of(new Tiposeccion());

        if (this.condicionDeProceso()) {
            /*
             Inicio Proceso para Inmuebles
             */
            ts = registroService.findTiposeccionById(LdRegistro.TIPOSECCION_INMUEBLE);
            if (ts.isPresent()) {
                tiposeccion = ts.get().getNombre().trim();
            }
            logService.auditar(null, tipo, origen, "INICIO - Solicitudes de " + tiposeccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
            procesarSolicitudService.procesarSolicitudInformeDeuda(LdRegistro.TIPOSECCION_INMUEBLE, origen);
            logService.auditar(null, tipo, origen, "FIN - Solicitudes de " + tiposeccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));

            /*
             Inicio Proceso para Tribunal de Faltas
             */
            ts = registroService.findTiposeccionById(LdRegistro.TIPOSECCION_TRIBUNAL);
            if (ts.isPresent()) {
                tiposeccion = ts.get().getNombre().trim();
            }
            logService.auditar(null, tipo, origen, "INICIO - Solicitudes de " + tiposeccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
            procesarSolicitudService.procesarSolicitudInformeDeuda(LdRegistro.TIPOSECCION_TRIBUNAL, origen);
            logService.auditar(null, tipo, origen, "FIN - Solicitudes de " + tiposeccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
        }
    }

    /**
     * ************************************************************************************
     * ************************************************************************************
     * REFERIDOS A INFORME DE CAUSAS DE FALTAS
     * ************************************************************************************
     * ************************************************************************************
     */
    /* Esta Funcion Envia Notificacion de Causas de Faltas Generadas por Autogestion a las 6 de la mañana de todos los días */
    @Async
//    @org.springframework.scheduling.annotation.Scheduled(initialDelay = SEGUNDO_10, fixedDelay = MINUTO_15)
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 6 * * ?", zone = TIME_ZONE)
    public void procesarInformeCausas() {
        if (this.condicionDeProceso()) {
            procesarCausa.procesarInformeCausasAutogestionadas(Fechas.sumarRestarDias(Fechas.fechaActualLocalDate(), -1L), Fechas.sumarRestarDias(Fechas.fechaActualLocalDate(), -1L));
        }
    }

    /**
     * ************************************************************************************
     * ************************************************************************************
     * REFERIDOS AL MODULO DE NOTIFICACIONES
     * ************************************************************************************
     * ************************************************************************************
     */
    /* Esta Funcion Ejecuta las Notificaciones Registradas Marcadas como Prioridad Inmediata */
    @Async
    @org.springframework.scheduling.annotation.Scheduled(initialDelay = SEGUNDO_15, fixedDelay = MINUTO_2)
    public void procesarEnvioNotificacionesInmediata() {
        String tipo = "INFO";
        String origen = "SCHEDULED";
        String seccion = "NOTIFICACION INMEDIATA";

        if (this.condicionDeProceso()) {
            nalogService.auditar(null, tipo, origen, "INICIO - Solicitudes con Prioridad " + seccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
            procesarNotificacion.procesarNotificacion(true);
            nalogService.auditar(null, tipo, origen, "FIN - Solicitudes con Prioridad  " + seccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
        }
    }

    /* Esta Funcion Ejecuta las Notificaciones Registradas No Marcadas como Prioridad Inmediata */
    @Async
    @org.springframework.scheduling.annotation.Scheduled(initialDelay = SEGUNDO_30, fixedDelay = MINUTO_5)
    public void procesarEnvioNotificaciones() {
        String tipo = "INFO";
        String origen = "SCHEDULED";
        String seccion = "NOTIFICACION PROGRAMADA";

        if (this.condicionDeProceso()) {
            nalogService.auditar(null, tipo, origen, "INICIO - Solicitudes con Prioridad " + seccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
            procesarNotificacion.procesarNotificacion(false);
            nalogService.auditar(null, tipo, origen, "FIN - Solicitudes con Prioridad  " + seccion, Fechas.getNowDateAsString("dd-MM-yyyy HH:mm:ss.SSS"));
        }
    }

    /**
     * ************************************************************************************
     * ************************************************************************************
     * REFERIDOS A MODULO EVENTOS
     * ************************************************************************************
     * ************************************************************************************
     */
    /* Esta Funcion Verifica los Envio de las Entradas que fueron adquiridas mediante el modulo de Eventos
     * En Particular este proceso es para el Evento de la Fiesta del Litoral 2024
     * Verifica Entradas No Enviadas al Comprador y que tiene Estado PAGADO
     */
    @Async
    @org.springframework.scheduling.annotation.Scheduled(initialDelay = MINUTO, fixedDelay = MINUTO_5)
    public void enviarEntradasPagadasPorEvento() {
        /**
         * Ejecuto Para Caso de Festival del Litoral 2024
         */
        if (this.condicionDeProceso()) {
            procesoenvioService.enviarEntradasPagadasPorEvento("LITORAL2024");
        }
    }

    /*
    PARA HACER 02 11 2023
    cuando ingresa un pago que bloquea el acta habria que controlar que si el acta esta en una causa,
    tambien se bloquee la causa
    esto lo hace en el trigger de estados del acta

     */

}