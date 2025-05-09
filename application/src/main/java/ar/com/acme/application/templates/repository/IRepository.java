package ar.com.acme.application.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ar.com.acme.application.templates.entity.IEntity;

import java.util.List;

/**
 * Esta interfase representa el comportamiento deseable de un repositorio de persistencia para el modelo
 * de datos del sistema. Hereda aqui de {@link JpaRepository} porque se trabaja con una base JPA subyacente,
 * pero justamente permite abstraer hacia arriba de la capa que finalmente implemente las operaciones
 * que se necesiten. Las implementaciones se espera que brinden los servicios necesarios para persistir cambios
 * al motor de persistencia, eliminar instancias de entidades del motor y aplicar mecanismos de auditoria,
 * siempre abtrayendo de las cuestiones 'fisicas' (de la implementacion concreta) al sistema en si.
 *
 * @param <U>   El tipo de la entidad servida
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20250505
 */
@NoRepositoryBean
public interface IRepository<U extends IEntity<TKI>, TKI> extends JpaRepository<U, TKI> {
    List<U> findByDeletedIsNull();

    default List<U> findAllAlive() {
        return findByDeletedIsNull();
    }
}
