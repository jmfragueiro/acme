package ar.com.acme.system.usuario;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Fechas;
import ar.com.acme.framework.common.Response;
import ar.com.acme.framework.common.Tools;
import ar.com.acme.framework.core.extradata.ReqScopeExtraData;
import ar.com.acme.ports.service.Servicio;
import ar.com.acme.ports.filemanager.FilemanagerRequest;
import ar.com.acme.ports.filemanager.FilemanagerResponse;
import ar.com.acme.ports.filemanager.IFilemanagerOnActionClient;
import ar.com.acme.ports.filemanager.FilemanagerRequest.EFilemanagerAction;
import ar.com.acme.ports.filemanager.FilemanagerRequest.EFilemanagerSubtipo;
import ar.com.acme.sistema.seguridad.auditoriausuario.AuditoriausuarioDTO;
import ar.com.acme.sistema.seguridad.auditoriausuario.AuditoriausuarioService;
import ar.com.acme.sistema.seguridad.permiso.Permiso;
import ar.com.acme.sistema.seguridad.usuariogrupo.IUsuarioGrupoService;
import ar.com.acme.sistema.notificacion.agenda.NaIAgendaService;
import ar.com.acme.sistema.notificacion.direccionenvio.NaIDireccionenvioService;
import ar.com.acme.sistema.notificacion.medio.NaMedio;
import ar.com.acme.sistema.notificacion.notificacion.NaINotificacionService;
import ar.com.acme.sistema.notificacion.notificacion.NaNotificacionAdjuntoDTO;
import ar.com.acme.sistema.notificacion.notificacion.NaNotificacionDetalleDTO;
import ar.com.acme.sistema.notificacion.origen.NaOrigen;
import ar.com.acme.sistema.persona.persona.Persona;
import ar.com.acme.sistema.tramite.areausuario.Areausuariovista;
import ar.com.acme.sistema.utils.UtilService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioService extends Servicio<Usuario, Long> implements IUsuarioService, IFilemanagerOnActionClient {
    private final String urlconsultaqr;
    private final String empresanombre;
    private final String sistemanombre;
    private final PasswordEncoder passwordEncoder;
    private final AuditoriausuarioService auditoriausuarioService;
    private final ReqScopeExtraData requestScopeExtraData;
    private final IUsuariovistaService usuariovistaService;
    private final NaIAgendaService agendaService;
    private final NaINotificacionService notificacionService;
    private final NaIDireccionenvioService direccionenvioService;
    private final IUsuarioGrupoService usuarioGrupoService;
    private final UtilService utilService;

    public UsuarioService(IUsuarioRepo repo,
                          @Value("${front.urlconsultaqr}") String urlconsultaqr,
                          @Value("${front.nombre}") String empresanombre,
                          @Value("${front.sistema}") String sistemanombre,
                          PasswordEncoder passwordEncoder,
                          AuditoriausuarioService auditoriausuarioService,
                          ReqScopeExtraData requestScopeExtraData,
                          IUsuariovistaService usuariovistaService,
                          NaIAgendaService agendaService,
                          NaINotificacionService notificacionService,
                          NaIDireccionenvioService direccionenvioService,
                          IUsuarioGrupoService usuarioGrupoService,
                          UtilService utilService) {
        super(repo);
        this.urlconsultaqr = urlconsultaqr;
        this.empresanombre = empresanombre;
        this.sistemanombre = sistemanombre;
        this.passwordEncoder = passwordEncoder;
        this.auditoriausuarioService = auditoriausuarioService;
        this.requestScopeExtraData = requestScopeExtraData;
        this.usuariovistaService = usuariovistaService;
        this.agendaService = agendaService;
        this.notificacionService = notificacionService;
        this.direccionenvioService = direccionenvioService;
        this.usuarioGrupoService = usuarioGrupoService;
        this.utilService = utilService;
    }

    public void actualizarFoto(Long id, String imagen) {
        Usuario usuario = this.findById(id).get();
        usuario.setFoto(imagen);
        this.persist(usuario);
    }

    @Override
    public boolean estaEnGrupoDePermisos(Long usuarioid, String grupo) {
        return usuarioGrupoService.findByUsuario(usuarioid).stream().anyMatch(g -> g.getGrupo().getName().equals(grupo));
    }

    @Override
    public Page<Usuario> findGeneral(String filtro, Pageable pageable) {
        return this.findByUsuarioOrEmail(filtro, pageable);
    }

    public Optional<Usuario> findByPersona(Persona persona) {
        return ((IUsuarioRepo) getRepo()).findByPersona(persona);
    }

    @Override
    public Optional<Usuario> findByTokenResetPassword(String token) {
        return ((IUsuarioRepo) getRepo()).findByTokenResetPassword(token);
    }

    @Override
    public Optional<Usuario> findByTokenCheckEmail(String token) {
        return ((IUsuarioRepo) getRepo()).findByTokenCheckEmail(token);
    }

    @Override
    public List<Areausuariovista> findTramiteAreaByUsername(String username) {
        return ((IUsuarioRepo) getRepo()).findTramiteAreaByUsername(username.toLowerCase());
    }

    @Override
    public List<String> getPermisosAsociados(Usuario usuario) {
        try {
            return usuario.getAuthorities().stream().map(p -> p.getAuthority()).collect(Collectors.toList());
        } catch (BeansException e) {
            return new ArrayList<>();
        }
    }

    // public UserDetails loadUserByUsername(String username) {
    //     ///////////////////////////////////////////////////////
    //     // ESTO ES PARA GENERAR USUARIOS Y CLAVES Y PROBAR:  //
    //     // (hay que debuggear y parar en la captura de pass  //
    //     //  y guardar el pass generado en la base de datos)  //
    //     ///////////////////////////////////////////////////////
    //     //Usuario us = new Usuario();
    //     //us.setUsername("lschwegler");
    //     //us.setPassword(passEncoder.encode("luis"));
    //     //String pass = us.getPassword();
    //     ///////////////////////////////////////////////////////
    //     return tokenService.obtenerTokenConexion(username);
    // }

    @Override
    public Optional<Usuario> findByName(String name) {
        return ((IUsuarioRepo) getRepo()).findByUsername(name);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return getRepo().findById(id);
    }

    @Override
    public Optional<Usuario> findByUsername(String usename) {
        return ((IUsuarioRepo) getRepo()).findByUsername(usename);
    }

    @Override
    public Optional<Usuario> findByUsernameOrEmail(String parametro) {
        return ((IUsuarioRepo) getRepo()).findByUsernameOrEmail(parametro);
    }

    @Override
    public Page<Usuario> findByUsuarioOrEmail(String filtro, Pageable pageable) {
        return ((IUsuarioRepo) this.getRepo()).findByUsuarioOrEmail(filtro, pageable);
    }

    @Override
    public Optional<String> getUsernameByUsuarioId(Long id) {
        return ((IUsuarioRepo) getRepo()).getUsernameByUsuarioId(id);
    }

    @Override
    public Usuario normalizarDatos(Usuario usuario) {
        usuario.setUsername(Tools.getlimpiarString(usuario.getUsername()).toLowerCase().trim());
        usuario.setEmail(Tools.getlimpiarString(usuario.getEmail()).toLowerCase().trim());

        return usuario;
    }

    @Override
    public List<Permiso> findPermisosPorUsuario(Usuario usuario) {
        return ((IUsuarioRepo) getRepo()).findPermisosPorUsuario(usuario);
    }

    @Override
    public void registrarLogin(String username) {
        Usuario usuario = ((IUsuarioRepo) getRepo()).findUsuarioByUsername(username.toLowerCase());
        usuario.setLastLogin(LocalDateTime.now());
        usuario.setTokenresetpassword(null);
        this.persist(usuario);
        this.postPersist(usuario, null, "login");
    }

    @Override
    public void registrarLogout(String username) {
        Usuario usuario = ((IUsuarioRepo) getRepo()).findUsuarioByUsername(username.toLowerCase());
        this.postPersist(usuario, null, "logout");
    }

    @Override
    public List<AuditoriausuarioDTO> auditoriaPorUsuario(Long usuarioid) {
        return ((IUsuarioRepo) getRepo()).auditoriaPorUsuario(usuarioid);
    }

    @Override
    public Response prePersist(Usuario usuario, Usuario usuariooriginal, String metodo) {
        return Response.ok();
    }

    @Override
    public void postPersist(Usuario usuario, Usuario usuariooriginal, String metodo) {
        /**
         * Tarea 1 del Post Persist
         * Audito la Actualizacion de Usuarios
         */
        String observacion;
        String referencia = null;
        String datofijo = " - Usuario: " + usuario.getUsername();
        switch (metodo) {
            case "add" -> {
                observacion = "Alta de Usuario" + datofijo;
                referencia = "NU";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "update" -> {
                if (!usuario.getUsername().equals(usuariooriginal.getUsername())) {
                    observacion = "Modifica USERNAME. Antes: " + usuariooriginal.getUsername() + " - Nuevo: " + usuario.getUsername();
                    referencia = "MD";
                    auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                }
                if (!usuario.getEmail().equals(usuariooriginal.getEmail())) {
                    usuario.setEmailcheckAt(null);
                    this.persist(usuario);

                    observacion = "Modifica EMAIL. Antes: " + usuariooriginal.getEmail() + " - Nuevo: " + usuario.getEmail() + datofijo;
                    referencia = "MD";
                    auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                }
                if (!usuario.getLocked().equals(usuariooriginal.getLocked())) {
                    observacion = "Modifica ESTADO BLOQUEADO. Antes: " + (usuariooriginal.getLocked() ? "SI" : "NO") + " - Nuevo: " + (usuario.getLocked() ? "SI" : "NO") + datofijo;
                    referencia = "MD";
                    auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                }
                if (!usuario.getEnabled().equals(usuariooriginal.getEnabled())) {
                    observacion = "Modifica ESTADO ACTIVO. Antes: " + (usuariooriginal.getEnabled() ? "SI" : "NO") + " - Nuevo: " + (usuario.getEnabled() ? "SI" : "NO") + datofijo;
                    referencia = "MD";
                    auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                }
                break;
            }
            case "solresetpas" -> {
                observacion = "Solicitud de Recuperacion de Clave" + datofijo;
                referencia = "SOLRESETPAS";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "proresetpas" -> {
                observacion = "Recuperacion de Clave" + datofijo;
                referencia = "PRORESETPAS";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "solchkemail" -> {
                observacion = "Solicitud de Confirmación de la Dirección de Correo Electrónico" + datofijo;
                referencia = "SOLCHKEMAIL";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "prochkemail" -> {
                observacion = "Proceso de Verificación del Correo Electrónico" + datofijo;
                referencia = "PROCHKEMAIL";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "chpassreset" -> {
                observacion = "Proceso de Cambio de Password - Se Efectua RESET" + datofijo;
                referencia = "CAMBIARPASS";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "chpass" -> {
                observacion = "Proceso de Cambio de Password" + datofijo;
                referencia = "CAMBIARPASS";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "chmail" -> {
                if (!usuario.getEmail().equals(usuariooriginal.getEmail())) {
                    usuario.setEmailcheckAt(null);
                    this.persist(usuario);
                }

                observacion = "Modifica EMAIL" + datofijo;
                referencia = "MD";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "login" -> {
                observacion = Constantes.MSJ_SES_INF_LOGGON + datofijo;
                referencia = "LOGIN";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "logout" -> {
                observacion = Constantes.MSJ_SES_INF_LOGGOFF + datofijo;
                referencia = "LOGOUT";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            case "delete" -> {
                observacion = "El Usuario fue Marcado como Borrado" + datofijo;
                referencia = "BU";
                auditoriausuarioService.auditar(usuario.getId(), referencia, observacion);
                break;
            }
            default -> {
                observacion = "Proceso Indefinido!!!";
                break;
            }
        }
    }

    @Override
    public Response registrarSolicitudRecuperarPassword(String username) {
        Response resultado;
        Optional<Usuario> usuario = ((IUsuarioRepo) getRepo()).findByUsername(username);

        if (usuario.isEmpty() || !usuario.get().isAlive()) {
            resultado = Response.fail(Constantes.MSJ_SEC_ERR_NOUSER);
        } else {
            if (!usuario.get().getEnabled()) {
                resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERENABLED);
            } else {
                if (usuario.get().getLocked()) {
                    resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERLOCKED);
                } else {
                    if (usuario.get().getEmailcheckAt() == null) {
                        resultado = Response.fail(Constantes.MSJ_SEC_ERR_USEREMAILNOTCHECK + ". No Se Puede Enviar Email para Iniciar el Proceso de Recuperación!");
                    } else {
                        try {
                            /* Registro el Token de Recuperación */
                            String textoacodificar = usuario.get().getId().toString() + Fechas.getNowDateAsString("");
                            String mi_token = utilService.getMD5TokenForPrefijoOrClase(textoacodificar, true, "generico_para_reset_password");
                            usuario.get().setTokenresetpassword(mi_token);

                            /**
                             * Envio Notificacion de Aviso
                             */

                            /* Armo contenido de la Notificacion */
                            String asunto_parcial = "Solicitud de Recuperación de la Password";
                            String asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                            String contenido =
                                    "<div>" +
                                            "<h2><strong>Estimado/a " + usuario.get().getPersona().getRazonsocial() + "</strong></h2>" +
                                            "</br>" +
                                            "<p>Usted ha Solicitado Iniciar el Proceso de Recuperación de la Clave de Acceso</p>" +
                                            "</br>" +
                                            "<p>para el usuario: <strong>" + usuario.get().getUsername() + "</strong></p>" +
                                            "</br>" +
                                            "<p>Para Efectivizar la Recuperación de su Password utilice el siguiente Link " + this.urlconsultaqr + mi_token + "</p>" +
                                            "</br>";

                            HashMap<String, String> datos = new HashMap<String, String>();
                            datos.put("usuario", "1");
                            datos.put("origen", NaOrigen.ORIGEN_SEGURIDAD.toString());
                            datos.put("medio", NaMedio.MEDIO_EMAIL.toString());
                            datos.put("direccionenvio", direccionenvioService.findDireccionPorDefecto(1L).getId().toString());
                            datos.put("descripcion", asunto_parcial);
                            datos.put("identificacion", "modulo,sg" + ";" + "clase,sg-user" + ";" + "id," + usuario.get().getId().toString());
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
                            detalles.add(new NaNotificacionDetalleDTO(usuario.get().getPersona().getId(), usuario.get().getEmail(), null, false, null, null));

                            Collection<NaNotificacionAdjuntoDTO> adjuntos = new ArrayList<>();

                            resultado = notificacionService.nuevaNotificacion(datos, adjuntos, detalles);

                            if (resultado.success()) {
                                /* Si se cumplen todos los pasos, Persisto definitivamente el Token de Recuperacion */
                                this.persist(usuario.get());
                                this.postPersist(usuario.get(), null, "solresetpas");
                                resultado = Response.ok("Se Ha Enviado una Notificación Informativa al Usuario para Iniciar el Proceso de Recuperación de la Password");
                            }
                        } catch (Exception ex) {
                            resultado = Response.fail(Constantes.MSJ_SEC_ERR_GENERICO + ex.getMessage());
                        }
                    }
                }
            }
        }

        return resultado;
    }

    @Override
    public Response procesarRecuperarPassword(String token) {
        Response resultado;
        Optional<Usuario> usuario = ((IUsuarioRepo) getRepo()).findByTokenResetPassword(token);

        if (usuario.isEmpty() || !usuario.get().isAlive()) {
            resultado = Response.fail(Constantes.MSJ_SEC_ERR_NOUSER_BY_TOKEN);
        } else {
            if (!usuario.get().getEnabled()) {
                resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERENABLED);
            } else {
                if (usuario.get().getLocked()) {
                    resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERLOCKED);
                } else {
                    try {
                        /* Registro la nueva Clave */
                        String clavetemporal = this.generarClaveTemporal(usuario.get());

                        usuario.get().setPassword(passwordEncoder.encode(clavetemporal));
                        usuario.get().setExpired(true);
                        usuario.get().setExpiresAt(Fechas.toLocalDate(new Date()));
                        usuario.get().setTokenresetpassword(null);

                        /**
                         * Envio Notificacion de Aviso
                         */

                        /* Armo contenido de la Notificacion */
                        String asunto_parcial = "Se ha Procesado su Solicitud de Recuperación del Password";
                        String asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                        String contenido =
                                "<div>" +
                                        "<h2><strong>Estimado/a " + usuario.get().getPersona().getRazonsocial() + "</strong></h2>" +
                                        "</br>" +
                                        "<p>Su usuario para ingresar al sistema es <strong>" + usuario.get().getUsername() + "</strong></p>" +
                                        "</br>" +
                                        "<p>Su clave fue regenerada y temporalmente es <strong>" + clavetemporal + "</strong>" + "</p>" +
                                        "</br>" +
                                        "<p>Deberá cambiarla en el próximo ingreso." + "</p>" +
                                        "</br>";

                        HashMap<String, String> datos = new HashMap<String, String>();
                        datos.put("usuario", "1");
                        datos.put("origen", NaOrigen.ORIGEN_SEGURIDAD.toString());
                        datos.put("medio", NaMedio.MEDIO_EMAIL.toString());
                        datos.put("direccionenvio", direccionenvioService.findDireccionPorDefecto(1L).getId().toString());
                        datos.put("descripcion", asunto_parcial);
                        datos.put("identificacion", "modulo,sg" + ";" + "clase,sg-user" + ";" + "id," + usuario.get().getId().toString());
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
                        detalles.add(new NaNotificacionDetalleDTO(usuario.get().getPersona().getId(), usuario.get().getEmail(), null, false, null, null));

                        Collection<NaNotificacionAdjuntoDTO> adjuntos = new ArrayList<>();

                        resultado = notificacionService.nuevaNotificacion(datos, adjuntos, detalles);

                        if (resultado.success()) {
                            /* Si se cumplen todos los pasos, Persisto definitivamente el Token de Recuperacion */
                            this.persist(usuario.get());
                            this.postPersist(usuario.get(), null, "proresetpas");
                            resultado = Response.ok("Se Ha Enviado una Notificación Informativa al Usuario con la Clave Temporal Generada");
                        }
                    } catch (Exception ex) {
                        resultado = Response.fail("Ha Ocurrido un Error Inesperado: " + ex.getMessage());
                    }
                }
            }
        }

        return resultado;
    }

    @Override
    public Response registrarSolicitudCheckEmail(String username, String email) {
        Response resultado;
        Usuariovista usuarioSesion = (Usuariovista) requestScopeExtraData.get();

        if (!usuarioSesion.getUsername().equals(username)) {
            resultado = Response.fail("No Puede Solicitar Confirmación de Direccion de Correo Electronico para Otro Usuario");
        } else {
            if (email.isEmpty() || email.isBlank()) {
                resultado = Response.fail("Debe Indicar la Dirección de Correo Electrónico");
            } else {
                if (email.equals(usuarioSesion.getDocumento().toString() + "@gmail.com")) {
                    resultado = Response.fail("Debe Indicar una Dirección de Correo Electrónico Válida - La que Solicita Validar no es Correcta");
                } else {
                    Optional<Usuario> usuario = ((IUsuarioRepo) getRepo()).findByUsername(username);
                    if (usuario.isEmpty() || !usuario.get().isAlive()) {
                        resultado = Response.fail(Constantes.MSJ_SEC_ERR_NOUSER);
                    } else {
                        if (!usuario.get().getEnabled()) {
                            resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERENABLED);
                        } else {
                            if (usuario.get().getLocked()) {
                                resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERLOCKED);
                            } else {
                                try {
                                    /* Registro el Token de Confirmacion de Email */
                                    String textoacodificar = usuario.get().getId().toString() + Fechas.getNowDateAsString("");
                                    String mi_token = utilService.getMD5TokenForPrefijoOrClase(textoacodificar, true, "generico_para_check_email_user");
                                    usuario.get().setEmail(email);
                                    usuario.get().setEmailchecktoken(mi_token);

                                    /**
                                     * Envio Notificacion de Aviso
                                     */

                                    /* Armo contenido de la Notificacion */
                                    String asunto_parcial = "Solicitud de Confirmación de la Dirección de Correo Electrónico";
                                    String asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                                    String contenido =
                                            "<div>" +
                                                    "<h2><strong>Estimado/a " + usuario.get().getPersona().getRazonsocial() + "</strong></h2>" +
                                                    "</br>" +
                                                    "<p>Usted ha Solicitado Iniciar el Proceso de Confirmación de su Dirección de Correo Electrónico</p>" +
                                                    "</br>" +
                                                    "<p>para el usuario: <strong>" + usuario.get().getUsername() + "</strong>, Correo Electrónico: <strong>" + usuario.get().getEmail() + "</strong></p>" +
                                                    "</br>" +
                                                    "<p>Para Efectivizar la Confirmación utilice el siguiente Link " + this.urlconsultaqr + mi_token + "</p>" +
                                                    "</br>";

                                    HashMap<String, String> datos = new HashMap<String, String>();
                                    datos.put("usuario", "1");
                                    datos.put("origen", NaOrigen.ORIGEN_SEGURIDAD.toString());
                                    datos.put("medio", NaMedio.MEDIO_EMAIL.toString());
                                    datos.put("direccionenvio", direccionenvioService.findDireccionPorDefecto(1L).getId().toString());
                                    datos.put("descripcion", asunto_parcial);
                                    datos.put("identificacion", "modulo,sg" + ";" + "clase,sg-user" + ";" + "id," + usuario.get().getId().toString());
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
                                    detalles.add(new NaNotificacionDetalleDTO(usuario.get().getPersona().getId(), usuario.get().getEmail(), null, false, null, null));

                                    Collection<NaNotificacionAdjuntoDTO> adjuntos = new ArrayList<>();

                                    resultado = notificacionService.nuevaNotificacion(datos, adjuntos, detalles);

                                    if (resultado.success()) {
                                        /* Si se cumplen todos los pasos, Persisto definitivamente el Token de Solicitud Confirmacion */
                                        this.persist(usuario.get());
                                        this.postPersist(usuario.get(), null, "solchkemail");
                                        resultado = Response.ok("Se Ha Enviado una Notificación Informativa al Usuario para Iniciar el Proceso de Confirmacion de Correo Electronico");
                                    }
                                } catch (Exception ex) {
                                    resultado = Response.fail("Ha Ocurrido un Error Inesperado: " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultado;
    }

    @Override
    public Response procesarCheckEmail(String token) {
        Response resultado;
        Optional<Usuario> usuario = ((IUsuarioRepo) getRepo()).findByTokenCheckEmail(token);

        if (usuario.isEmpty() || !usuario.get().isAlive()) {
            resultado = Response.fail(Constantes.MSJ_SEC_ERR_NOUSER_BY_TOKEN);
        } else {
            if (!usuario.get().getEnabled()) {
                resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERENABLED);
            } else {
                if (usuario.get().getLocked()) {
                    resultado = Response.fail(Constantes.MSJ_SEC_ERR_USERLOCKED);
                } else {
                    try {
                        /* Registro el Check del Correo Electronico */
                        usuario.get().setEmailcheckAt(Fechas.fechaActualLocalDateTime());
                        usuario.get().setEmailchecktoken(null);

                        /**
                         * Envio Notificacion de Aviso
                         */

                        /* Armo contenido de la Notificacion */
                        String asunto_parcial = "Se ha Verificado su Dirección de Correo Electrónico";
                        String asunto = empresanombre + " - " + sistemanombre + " - " + asunto_parcial;
                        String contenido =
                                "<div>" +
                                        "<h2><strong>Estimado/a " + usuario.get().getPersona().getRazonsocial() + "</strong></h2>" +
                                        "</br>" +
                                        "<p>Su Dirección de Correo Electrónico <strong>" + usuario.get().getEmail() + "</strong> fue Verificada.</p>" +
                                        "</br>" +
                                        "<p>y asociada al usuario <strong>" + usuario.get().getUsername() + "</strong></p>" +
                                        "</br>";

                        HashMap<String, String> datos = new HashMap<String, String>();
                        datos.put("usuario", "1");
                        datos.put("origen", NaOrigen.ORIGEN_SEGURIDAD.toString());
                        datos.put("medio", NaMedio.MEDIO_EMAIL.toString());
                        datos.put("direccionenvio", direccionenvioService.findDireccionPorDefecto(1L).getId().toString());
                        datos.put("descripcion", asunto_parcial);
                        datos.put("identificacion", "modulo,sg" + ";" + "clase,sg-user" + ";" + "id," + usuario.get().getId().toString());
                        datos.put("prioridad", "2");
                        datos.put("respondera", "noresponder@posadas.gov.ar");
                        datos.put("asunto", asunto);
                        datos.put("cuerpo", contenido);
                        datos.put("textolibre", "");
                        datos.put("observacion", "");
                        datos.put("activado", "1");
                        datos.put("procesado", "0");
                        datos.put("procesadoerror", "0");

                        Collection<NaNotificacionDetalleDTO> detalles = new ArrayList<>();
                        detalles.add(new NaNotificacionDetalleDTO(usuario.get().getPersona().getId(), usuario.get().getEmail(), null, false, null, null));

                        Collection<NaNotificacionAdjuntoDTO> adjuntos = new ArrayList<>();

                        resultado = notificacionService.nuevaNotificacion(datos, adjuntos, detalles);


                        if (resultado.success()) {
                            /* Si se cumplen todos los pasos, Persisto definitivamente la Confirmacion del Corre Electronico */
                            this.persist(usuario.get());
                            this.postPersist(usuario.get(), null, "prochkemail");
                            resultado = Response.ok("Se Ha Enviado una Notificación Informativa al Usuario confirmando la Verificacion Correcta de la Dirección de Correo Electrónico");
                        }
                    } catch (Exception ex) {
                        resultado = Response.fail("Ha Ocurrido un Error Inesperado: " + ex.getMessage());
                    }
                }
            }
        }

        return resultado;
    }

    @Override
    public String generarClaveTemporal(Usuario usuario) {
        String parte1 = usuario.getPersona().getNombre().split(" ")[0].toLowerCase();
        String parte2 = Tools.cadenaAleatoria(4).toLowerCase();
        String clavetemporal = parte1 + "_" + parte2;

        return clavetemporal;
    }

    @Override
    public List<Usuariovistaactividad> actividadesPorUsuarioFecha(Long usuarioid, String fecha) {
        return ((IUsuarioRepo) getRepo()).actividadesPorUsuarioFecha(usuarioid, LocalDate.parse(fecha));
    }

    @Override
    public List<NotificacionusuarioDTO> notificacionesPorUsuario(Long usuarioid) {
        /**
         * En Este Proceso hay que ir evaluando todas las notificaciones que se quieren hacer al usuario
         * e ir agregandolas al set de resultados a devolver
         */
        Optional<Usuariovista> usuario = usuariovistaService.findById(usuarioid);
        List<NotificacionusuarioDTO> notificaciones = new ArrayList<>();

        if (usuario.isPresent() && usuario.get().isAlive()) {
            /* CASO 1 - No Tiene el Mail Validado y Tampoco lo solicito */
            if (usuario.get().getEmailcheckAt() == null && usuario.get().getEmailchecktoken() == null) {
                notificaciones.add(
                    new NotificacionusuarioDTO(
                        usuario.get().getId(),
                        usuario.get().getUsername(),
                        usuario.get().getRazonsocial(),
                        Fechas.fechaActualLocalDate(),
                        Fechas.fechaActualLocalDate(),
                        Fechas.horaActual(),
                        "Correo Electrónico del Usuario",
                        "Validacion de Correo Electrónico",
                        "Debe Efectuar la Validación de su Dirección de Correo Electrónico",
                        "mdi mdi-email",
                        "info"));
            }

            /* CASO 2 - Veo si tiene pedido de chequeo de mail y todavia no respondio el Mail */
            if (usuario.get().getEmailcheckAt() == null && usuario.get().getEmailchecktoken() != null) {
                notificaciones.add(
                    new NotificacionusuarioDTO(
                        usuario.get().getId(),
                        usuario.get().getUsername(),
                        usuario.get().getRazonsocial(),
                        Fechas.fechaActualLocalDate(),
                        Fechas.fechaActualLocalDate(),
                        Fechas.horaActual(),
                        "Correo Electrónico del Usuario",
                        "Validacion de Correo Electrónico",
                        "Solicitó Validar su Dirección de Correo Electrónico y todavía No Inició el Proceso con el Email Enviado a su Casilla!",
                        "mdi mdi-email",
                        "warning"));
            }

            /* CASO 3 - Veo si tiene Notificaciones en su Agenda */
            agendaService.findByUsuarioFecha(usuario.get().getId(), Fechas.fechaActualLocalDate()).forEach(a -> {
                notificaciones.add(
                    new NotificacionusuarioDTO(
                        usuario.get().getId(),
                        usuario.get().getUsername(),
                        usuario.get().getRazonsocial(),
                        a.getDesde(),
                        a.getHasta(),
                        a.getHorainicio(),
                        "Eventos de Agenda",
                        "Solicitó ser Notificado en su Agenda",
                        a.getDescripcion() + "!",
                        "mdi mdi-calendar-today",
                        "success"));
            });
        }

        return notificaciones;
    }

    @Override
    public boolean isFilemanagerClientForRequest(FilemanagerRequest fmreq) {
        return fmreq.tipo().equalsIgnoreCase("usu");
    }

    @Override
    public void filemanagerPosAction(FilemanagerResponse response) {
        var fmreq = response.request();

        if (fmreq != null) {
            if (fmreq.subtipo().equals(EFilemanagerSubtipo.FOTO)) {
                if (fmreq.action().equals(EFilemanagerAction.UPLOAD)) {
                    actualizarFoto(fmreq.id(), response.nombreArchivo());
                }

                if (fmreq.action().equals(EFilemanagerAction.DELETE)) {
                    actualizarFoto(Long.valueOf(fmreq.id()), null);
                }
            }
        }
    }
}
