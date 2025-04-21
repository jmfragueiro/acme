package ar.com.acme.ports.entity;

import ar.com.acme.framework.common.Constantes;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Esta clase abstracta representa el concepto de una Entidad que posee mecanismos que
 * permiten establecer una cierta trazabilidad de los cambios realizados sobre la misma.
 * Para ello se incorporan los atributos de fechas de alta, ultima modificacion y baja.
 * Se espera que toda entidad persistente y administrada de los sistemas creados con el
 * framework sean auditables y por ello hereden e imeplementen efectivmente de esta interfaz.
 *
 * @author jmfragueiro
 * @version 20200201
 */
@MappedSuperclass
public abstract class Entidad implements IEntidad<Long>, Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    //Luis 04-03-21
    //El nombre de la secuencia de redefine en cada entidad que hereda la presente clase abstracta
    //@SequenceGenerator(name = "id_generator", sequenceName = "seq_entidad", allocationSize = 1)
    private Long id;

    @Column(name = "fechaalta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaalta;

    @Column(name = "fechaumod")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaumod;

    @Column(name = "fechabaja")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechabaja;

    @PrePersist
    protected void setAltaData() {
        fechaalta = fechaumod = LocalDateTime.now();
    }

    @PreUpdate
    protected void setUmodData() {
        fechaumod = LocalDateTime.now();
    }

    @Override
    public boolean isNew() {
        return (id == null);
    }

    @Override
    public void onPersist() {
    }

    @Override
    public void onDelete() {
        fechabaja = LocalDateTime.now();
    }

    @Override
    public boolean isAlive() {
        return (fechabaja == null);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public LocalDateTime getFechaalta() {
        return fechaalta;
    }

    @Override
    public LocalDateTime getFechaumod() {
        return fechaumod;
    }

    @Override
    public LocalDateTime getFechabaja() {
        return fechabaja;
    }

    @Override
    public boolean equals(Object other) {
        return ((id != null) && (this == other || ((other instanceof Entidad) && id.equals(((Entidad) other).getId()))));
    }

    @Override
    public int hashCode() {
        return (43 * 5 + (getId() == null ? 0 : getId().hashCode()));
    }

    @Override
    public String toString() {
        return Constantes.SYS_CAD_OPENTYPE.concat(getClass().getSimpleName())
                .concat(Constantes.SYS_CAD_REFER)
                .concat(isNew() ? Constantes.SYS_CAD_NEW : getId().toString())
                .concat(Constantes.SYS_CAD_CLOSETPE);
    }
}
