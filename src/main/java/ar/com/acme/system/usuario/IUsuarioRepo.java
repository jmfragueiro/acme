package ar.com.acme.system.usuario;

import ar.com.acme.sistema.seguridad.auditoriausuario.AuditoriausuarioDTO;
import ar.com.acme.sistema.seguridad.permiso.Permiso;
import ar.com.acme.adapter.repos.IRepository;
import ar.com.acme.sistema.persona.persona.Persona;
import ar.com.acme.sistema.tramite.areausuario.Areausuariovista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepo extends IRepository<Usuario, Long> {
    @Query("select u from Usuario u where lower(u.username) like ?1 ")
    Usuario findUsuarioByUsername(String username);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByPersona(Persona persona);

    @Query("SELECT u FROM Usuario u WHERE u.tokenresetpassword = ?1 ")
    Optional<Usuario> findByTokenResetPassword(String token);

    @Query("SELECT u FROM Usuario u WHERE u.emailchecktoken = ?1 ")
    Optional<Usuario> findByTokenCheckEmail(String token);

    @Query("SELECT u.username FROM Usuario u WHERE u.id = ?1 ")
    Optional<String> getUsernameByUsuarioId(Long id);

    /**
     * Luis
     * No uso % en los Like, debido a que necesito que la busqueda sea exacta
     * Este método es usado en el proceso de alta de usuarios para verificar
     * la no duplicación de username o email en los datos registrados
     */
    @Query("select u from Usuario u where lower(u.username) like ?1 or lower(u.email) like ?1 ")
    Optional<Usuario> findByUsernameOrEmail(String parametro);

    @Query("select p " +
            " from ar.com.acme.seguridad.permiso.Permiso p " +
            "where p.fechabaja is null " +
            "  and p in (select g.permiso " +
            "              from GrupoPermiso g" +
            "             where g.grupo in (select u.grupo" +
            "                                 from UsuarioGrupo u" +
            "                                where u.usuario = ?1 " +
            "                                  and u.fechabaja is null))")
    List<Permiso> findPermisosPorUsuario(Usuario u);

    @Query("select u from Usuario u where u.username like %?1% or u.email like %?1% ")
    Page<Usuario> findByUsuarioOrEmail(String filtro, Pageable pager);

    /**
     * Luis
     * No uso % en los Like, debido a que necesito que la busqueda sea exacta
     */
    @Query("select tauv from Areausuariovista tauv " +
            " WHERE lower(tauv.username) like ?1 ")
    List<Areausuariovista> findTramiteAreaByUsername(String username);

    @Query(value = "select new ar.com.acme.seguridad.auditoriausuario.AuditoriausuarioDTO(au.usuario, aut.descripcion, us.username, au.observacion, au.fechaalta, pe.razonsocial, pe.imagen)" +
            " FROM Auditoriausuario  au" +
            " LEFT JOIN Auditoriausuariotipo aut ON (aut.id = au.auditoriausuariotipo)" +
            " LEFT JOIN Usuario us ON ( us.id = au.usuarioprocesa)" +
            " LEFT JOIN Persona pe ON ( pe.id = us.persona.id)" +
            " WHERE au.usuario = ?1" +
            " ORDER BY au.fechaalta, au.id")
    List<AuditoriausuarioDTO> auditoriaPorUsuario(Long usuarioid);

    @Query(value = "select uva" +
            " FROM Usuariovistaactividad uva" +
            " WHERE uva.usuario = ?1" +
            " AND uva.fecha = ?2" +
            " ORDER BY uva.fechaalta desc")
    List<Usuariovistaactividad> actividadesPorUsuarioFecha(Long usuario, LocalDate fecha);
}
