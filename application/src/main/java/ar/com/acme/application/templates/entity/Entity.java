package ar.com.acme.application.templates.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.acme.commons.Constants;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
        return Constants.SYS_CAD_OPENTYPE.concat(getClass().getSimpleName())
                .concat(Constants.SYS_CAD_REFER)
                .concat(isNew() ? Constants.SYS_CAD_ENTITY_NEW : getId().toString())
                .concat(Constants.SYS_CAD_CLOSETPE);
    }
}
