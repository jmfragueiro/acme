package ar.gov.posadas.mbe.sistema.seguridad.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import ar.gov.posadas.mbe.ports.entity.Entidad;

@Entity
@Table(name = "view_sg_accionesusuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuariovistaactividad extends Entidad {
    @Column(name = "usuario")
    private Long usuario;

    @Column(name = "modulo")
    private String modulo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "fecha")
    private LocalDate fecha;
}
