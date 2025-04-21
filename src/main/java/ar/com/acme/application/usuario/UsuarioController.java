package ar.gov.posadas.mbe.sistema.seguridad.usuario;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Fechas;
import ar.gov.posadas.mbe.framework.common.Response;
import ar.gov.posadas.mbe.framework.core.exception.SecurityException;
import ar.gov.posadas.mbe.ports.control.Controlador;
import ar.gov.posadas.mbe.ports.repos.ItemNotFoundException;
import ar.gov.posadas.mbe.sistema.seguridad.auditoriausuario.AuditoriausuarioDTO;
import ar.gov.posadas.mbe.sistema.notificacion.direccionenvio.NaIDireccionenvioService;
import ar.gov.posadas.mbe.sistema.notificacion.medio.NaMedio;
import ar.gov.posadas.mbe.sistema.notificacion.notificacion.NaINotificacionService;
import ar.gov.posadas.mbe.sistema.notificacion.notificacion.NaNotificacionAdjuntoDTO;
import ar.gov.posadas.mbe.sistema.notificacion.notificacion.NaNotificacionDetalleDTO;
import ar.gov.posadas.mbe.sistema.notificacion.origen.NaOrigen;
import ar.gov.posadas.mbe.sistema.tramite.areausuario.Areausuariovista;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends Controlador<Usuario, Long> {
    private final String frontendhostbasico;
    private final String empresanombre;
    private final String sistemanombre;
    private final PasswordEncoder passwordEncoder;
    private final NaINotificacionService notificacionService;
    private final NaIDireccionenvioService direccionenvioService;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioController(IUsuarioService servicio,
                            PasswordEncoder passwordEncoder,
                            NaINotificacionService notificacionService,
                            NaIDireccionenvioService direccionenvioService,
                            @Value("${front.frontendhostbasico}") String frontendhostbasico,
                            @Value("${front.nombre}") String empresanombre,
                            @Value("${front.sistema}") String sistemanombre) {
        super(servicio);
        this.passwordEncoder = passwordEncoder;
        this.notificacionService = notificacionService;
        this.direccionenvioService = direccionenvioService;
        this.frontendhostbasico = frontendhostbasico;
        this.empresanombre = empresanombre;
        this.sistemanombre = sistemanombre;
    }

    @GetMapping(path = "/ref/{key}", produces = "application/json")
    public Usuario viewPorUsernameOrEmail(@PathVariable("key") String key, HttpServletRequest req) {
        /**
         * Luis
         * Transformo a minúsculas el parametro recibido a fin
         * de unificar el método de búequeda.
         * En el repositorio se respetará esta critério
         */
        var parametro = key.toLowerCase();
        return viewdit(((IUsuarioService) getServicio())
                .findByUsernameOrEmail(parametro)
                .orElseThrow(() -> new ItemNotFoundException(getNombreEntidadAsociada(), parametro)).getId(), req)
                .body();
    }

    /**
     * Este Medoto retorna el area actual asignada al usuario en el Sistema de Tramites!!!
     */
    @GetMapping(path = "/tramitearea/{key}", produces = "application/json")
    public List<Areausuariovista> viewPorAreaTramite(@PathVariable("key") String key, HttpServletRequest req) {
        return ((IUsuarioService) getServicio()).findTramiteAreaByUsername(key);
    }

    @PutMapping(path = "/{key}/chpass")
    public ResponseEntity<Object> updatePASS(@PathVariable("key") String key,
                                             @RequestParam(required = false) String oldPassword,
                                             @Valid @Size(min = 3) @RequestParam String newPassword,
                                             @RequestParam String metodo) {
        try {
            Usuario usuario = ((IUsuarioService) getServicio())
                    .findByUsername(key)
                    .orElseThrow(() -> new ItemNotFoundException(getNombreEntidadAsociada(), key));

            /**
             * En Este IF se pregunta indistintamente la igualdad de oldPassword y usuario.getPassword
             * usando passwordEncoder y no usandolo ya que en el caso de recibir la peticion para realizar
             * un cambio por perfil de usuario, este último deberá ingresarla y este método la recibirá sin encriptar,
             * la otra alternativa es cuando se resetea la password del usuario por algun motivo y se le envía una por
             * defecto, en este caso al ingresar por primera vez se le solicita en cambio, procedimiento que no vuelve
             * a solicitar la clave vieja ya que fue ingresada para llegar al cambio definitivo, en esta oportunidad
             * este método recibe la clave vieja pero encriptada que la obtiene del objeto usuario en el front.
             */
            if (oldPassword == null
                    || oldPassword.isEmpty()
                    || passwordEncoder.matches(oldPassword, usuario.getPassword())
                    || oldPassword.equals(usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(newPassword));

                if (metodo.equals("RESET") || metodo.equals("ALTAUSUARIO")) {
                    usuario.setExpired(true);
                    usuario.setExpiresAt(Fechas.toLocalDate(new Date()));
                } else {
                    usuario.setExpired(false);
                    usuario.setExpiresAt(null);
                }

                getServicio().persist(usuario);

                if (metodo.equals("RESET") || metodo.equals("ALTAUSUARIO")) {
                    /**
                     * Envio Notificacion de Aviso
                     */
                    /* Armo contenido del Correo */
                    String asunto_parcial = "";
                    String contenido = "";
                    String asunto = "";
                    if (metodo.equals("RESET")) {
                        asunto_parcial = "Blanqueo de Clave de Ingreso al Sistema de Géstion";
                        asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                        contenido =
                                "<div>" +
                                        "<h2><strong>Estimado/a " + usuario.getPersona().getRazonsocial() + "</strong></h2>" +
                                        "</br>" +
                                        "<p>Su usuario para ingresar al sistema es <strong>" + usuario.getUsername() + "</strong>.</p>" +
                                        "</br>" +
                                        "<p>Su clave fue regenerada y temporalmente es <strong>" + newPassword + "</strong></p>" +
                                        "</br>" +
                                        "<p>Deberá cambiarla en el próximo ingreso.</p>" +
                                        "</br>";
                    } else {
                        asunto_parcial = "Registro de Nuevo Usuario " + usuario.getUsername();
                        asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                        contenido =
                                "<div>" +
                                        "<h2><strong>Bienvenido/a " + usuario.getPersona().getRazonsocial() + "</strong></h2>" +
                                        "</br>" +
                                        "<p>Su usuario para ingresar al sistema es <strong>" + usuario.getUsername() + "</strong>.</p>" +
                                        "</br>" +
                                        "<p>Su clave temporal es <strong>" + newPassword + "</strong></p>" +
                                        "</br>" +
                                        "<p>Deberá cambiarla en el próximo ingreso.</p>" +
                                        "</br>" +
                                        "<p>Link de ingreso al sistema <strong>" + frontendhostbasico + "</strong></p>" +
                                        "</br>";
                    }

                    HashMap<String, String> datos = new HashMap<String, String>();
                    datos.put("usuario", "1");
                    datos.put("origen", NaOrigen.ORIGEN_SEGURIDAD.toString());
                    datos.put("medio", NaMedio.MEDIO_EMAIL.toString());
                    datos.put("direccionenvio", direccionenvioService.findDireccionPorDefecto(1L).getId().toString());
                    datos.put("descripcion", asunto_parcial);
                    datos.put("identificacion", "modulo,sg" + ";" + "clase,sg-user" + ";" + "id," + usuario.getId().toString());
                    datos.put("prioridad", "0");
                    datos.put("respondera", "noresponder@posadas.gov.ar");
                    datos.put("asunto", asunto);
                    datos.put("cuerpo", contenido);
                    datos.put("textolibre", "");
                    datos.put("observacion", "");
                    datos.put("activado", "1");
                    datos.put("procesado", "0");
                    datos.put("procesadoerror", "0");

                    Collection<NaNotificacionDetalleDTO> detalles = new ArrayList<>();
                    detalles.add(new NaNotificacionDetalleDTO(usuario.getPersona().getId(), usuario.getEmail(), null, false, null, null));

                    Collection<NaNotificacionAdjuntoDTO> adjuntos = new ArrayList<>();

                    var resultado = notificacionService.nuevaNotificacion(datos, adjuntos, detalles);

                    if (resultado.success()) {
                        /* Si se cumplen todos los pasos, Persisto definitivamente el Token de Solicitud Confirmacion */
                        ((IUsuarioService) getServicio()).persist(usuario);
                    }
                }

                /**
                 * Audito el Cambio de Password
                 */
                if (metodo.equals("RESET")) {
                    getServicio().postPersist(usuario, null, "chpassreset");
                } else {
                    getServicio().postPersist(usuario, null, "chpass");
                }

                return ResponseEntity.ok().build();
            } else {
                throw new SecurityException(Constantes.MSJ_APP_CHANGEPASS_ERR_ONCHANGE, Constantes.SYS_APP_CHANGEPASS_ERR_BADPASS);
            }
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new HashMap<>().put(Constantes.SYS_APP_TXTLOGGIN_PASS.toUpperCase(), ex.getMessage()));
        }
    }

    @PutMapping(path = "/{key}/chemail")
    public ResponseEntity<Object> updateEMAIL(@PathVariable("key") String key,
                                              @RequestParam(required = false) String oldPassword,
                                              @Valid @Size(min = 4, max = 255) @RequestParam String email) {
        try {
            /* Obtengo Usuario Original y lo saco del entity manager, ya que es requerido en la Auditoria */
            Usuario usuarioOriginal = ((IUsuarioService) getServicio())
                    .findByUsername(key)
                    .orElseThrow(() -> new ItemNotFoundException(getNombreEntidadAsociada(), key));
            entityManager.detach(usuarioOriginal);

            Usuario usuario = ((IUsuarioService) getServicio())
                    .findByUsername(key)
                    .orElseThrow(() -> new ItemNotFoundException(getNombreEntidadAsociada(), key));

            if (passwordEncoder.matches(oldPassword, usuario.getPassword())) {
                usuario.setEmail(email);
                getServicio().persist(usuario);
                getServicio().postPersist(usuario, usuarioOriginal, "chmail");
                return ResponseEntity.accepted().build();
            } else {
                throw new SecurityException(Constantes.MSJ_APP_CHANGEEMAIL_ERR_ONCHANGE, Constantes.SYS_APP_CHANGEEMAIL_ERR_BADEMAIL);
            }
        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new HashMap<>().put(Constantes.SYS_APP_TXTLOGGIN_EMAIL, ex.getMessage()));
        }
    }

    @GetMapping(path = "/auditoria/{key}", produces = "application/json")
    public List<AuditoriausuarioDTO> auditoriaPorUsuario(@PathVariable("key") Long key) {
        return ((IUsuarioService) getServicio()).auditoriaPorUsuario(key);
    }

    @GetMapping(path = "/notificaciones/{key}", produces = "application/json")
    public List<NotificacionusuarioDTO> notificacionesPorUsuario(@PathVariable("key") Long usuarioId) {
        return ((IUsuarioService) getServicio()).notificacionesPorUsuario(usuarioId);
    }

    @GetMapping(path = "/actividades/{key}/{key1}", produces = "application/json")
    public List<Usuariovistaactividad> actividadesPorUsuarioFecha(@PathVariable("key") Long usuarioId,
                                                                  @PathVariable("key1") String fecha) {
        return ((IUsuarioService) getServicio()).actividadesPorUsuarioFecha(usuarioId, fecha);
    }

    @PostMapping(path = "/{key}/{key1}/solicitacheckemail")
    public ResponseEntity<Response> registrarSolicitudCheckEmail(@PathVariable("key") String username,
                                                                         @PathVariable("key1") String email) {

        Response resultado = ((IUsuarioService) getServicio()).registrarSolicitudCheckEmail(username, email);

        if (resultado.success()) {
            return ResponseEntity.ok().body(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
}
