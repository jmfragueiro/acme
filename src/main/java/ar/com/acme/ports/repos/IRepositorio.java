package ar.com.acme.ports.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ar.com.acme.framework.core.search.CustomFilter;
import ar.com.acme.framework.core.search.Predicado;
import ar.com.acme.ports.entity.IEntidad;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Esta interfase representa el comportamiento deseable de un repositorio de persistencia para el modelo
 * de datos del sistema. Hereda aqui de JpaRepository porque se trabaja con una base JPA subyacente, pero
 * justamente permite abstraer hacia arriba de la capa que finalmente implemente en la realidad las opers
 * que se necesiten. Las imlementaciones se espera que brinden los servs necesarios para persistir cambios
 * al motor de persistencia, eliminar instancias de entidades del motor y aplicar mecanismos de auditoria,
 * siempre abtrayendo de las cuestiones 'fisicas' (de la implementacion concreta) al sistema en si.
 *
 * @param <U>   El tipo de la entidad servida
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
@NoRepositoryBean
public interface IRepositorio<U extends IEntidad<TKI>, TKI> extends JpaRepository<U, TKI> {
    Page<U> findByFechabajaIsNull(Pageable pageable);

    List<U> findByFechabajaIsNull();

    default Page<U> findAllAlive(Pageable pageable) {
        return findByFechabajaIsNull(pageable);
    }

    default List<U> findAllAlive() {
        return findByFechabajaIsNull();
    }

    default Long countAllAlive() {
        return Integer.toUnsignedLong(findAllAlive().size());
    }

    default Page<U> getDataFromCustomFilter(List<Predicado> conditions, Pageable page, EntityManager em, Class<U> clase) {
        return new CustomFilter<U>().getDataFromCustomFilter(conditions, page, em, clase);
    }
}
