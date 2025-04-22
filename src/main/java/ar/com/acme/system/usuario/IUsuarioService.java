package ar.com.acme.system.usuario;

import ar.com.acme.framework.common.Response;
import ar.com.acme.framework.core.token.ITokenPrincipalService;
import ar.com.acme.ports.service.IServicio;
import ar.com.acme.sistema.seguridad.auditoriausuario.AuditoriausuarioDTO;
import ar.com.acme.sistema.seguridad.permiso.Permiso;
import ar.com.acme.sistema.persona.persona.Persona;
import ar.com.acme.sistema.tramite.areausuario.Areausuariovista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService extends IServicio<Usuario, Long>, ITokenPrincipalService<Usuario> {
    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByUsername(String usename);

    Optional<Usuario> findByPersona(Persona persona);

    Optional<Usuario> findByTokenResetPassword(String token);

    Optional<Usuario> findByTokenCheckEmail(String token);

    Optional<Usuario> findByUsernameOrEmail(String parametro);

    Page<Usuario> findByUsuarioOrEmail(String filtro, Pageable pager);

    Optional<String> getUsernameByUsuarioId(Long id);

    List<String> getPermisosAsociados(Usuario usuario);

    List<Permiso> findPermisosPorUsuario(Usuario usuario);

    List<Areausuariovista> findTramiteAreaByUsername(String username);

    List<AuditoriausuarioDTO> auditoriaPorUsuario(Long usuarioid);

    List<NotificacionusuarioDTO> notificacionesPorUsuario(Long usuarioid);

    List<Usuariovistaactividad> actividadesPorUsuarioFecha(Long usuarioid, String fecha);

    Response registrarSolicitudRecuperarPassword(String username);

    Response procesarRecuperarPassword(String token);

    Response registrarSolicitudCheckEmail(String username, String email);

    Response procesarCheckEmail(String token);

    String generarClaveTemporal(Usuario usuario);

    void registrarLogin(String username);

    void registrarLogout(String username);

    boolean estaEnGrupoDePermisos(Long usuarioid, String grupo);
}
