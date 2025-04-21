package ar.gov.posadas.mbe.sistema.seguridad.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import ar.gov.posadas.mbe.ports.entity.Entidad;

@Entity
@Table(name = "view_sg_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuariovista extends Entidad {
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "emailcheck_at")
    private LocalDateTime emailcheckAt;

    @Column(name = "emailchecktoken")
    private String emailchecktoken;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "foto")
    private String foto;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "persona")
    private Long persona;

    @Column(name = "documento")
    private Integer documento;

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "razonsocial")
    private String razonsocial;

    @Column(name = "genero")
    private String genero;

    @Column(name = "fechanacimiento")
    private LocalDateTime fechanacimiento;

    @Column(name = "imagen")
    private String imagen;
}
