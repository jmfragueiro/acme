package ar.com.acme.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.acme.base.common.BaseConstants;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Getter
public abstract class Entity implements IEntity<UUID>, Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Column(name = "modified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;

    @Column(name = "deleted")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleted;

    @PrePersist
    protected void setCreatedData() {
        created = modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void setModifiedData() {
        modified = LocalDateTime.now();
    }

    @Override
    public boolean isNew() {
        return (id == null);
    }

    @Override
    public LocalDateTime kill() {
        deleted = LocalDateTime.now();
        return deleted;
    }

    @Override
    public boolean isAlive() {
        return (deleted == null);
    }

    @Override
    public boolean equals(Object other) {
        return ((id != null) && (this == other || ((other instanceof Entity) && id.equals(((Entity) other).getId()))));
    }

    @Override
    public int hashCode() {
        return (43 * 5 + (getId() == null ? 0 : getId().hashCode()));
    }

    @Override
    public String toString() {
        return BaseConstants.SYS_CAD_OPENTYPE.concat(getClass().getSimpleName())
                .concat(BaseConstants.SYS_CAD_REFER)
                .concat(isNew() ? BaseConstants.SYS_CAD_ENTITY_NEW : getId().toString())
                .concat(BaseConstants.SYS_CAD_CLOSETPE);
    }
}
