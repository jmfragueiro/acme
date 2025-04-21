package ar.gov.posadas.mbe.framework.core.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import ar.gov.posadas.mbe.framework.common.Constantes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomFilter<U extends Object> {
    private Collection<Predicate> getPredicadosPorDefault(CriteriaBuilder cb, Root<U> root) {
        // Antes que nada se fuerza la condicion de que el registro no este
        // en condicion BAJA, suponiendo que todas las entidades tienen un
        // campo llamado fechabaja
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isNull(root.get("fechabaja")));
        return predicates;
    }

    public Page<U> getDataFromCustomFilter(List<Predicado> conditions, Pageable page, EntityManager em, Class<U> clase) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<U> query = cb.createQuery(clase);
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        Root<U> root = query.from(clase);
        List<Order> orders = new ArrayList<>();

        // Establezco las Condiciones por defecto de la Consulta
        List<Predicate> predicates = new ArrayList<>(getPredicadosPorDefault(cb, root));

        // establezco las condiciones propias de la consulta
        conditions.forEach(p -> {
            switch (p.getOp()) {
                case EQUAL -> predicates.add(
                        switch (p.getTipo()) {
                            case DATE -> cb.equal(root.get(p.getField()), (LocalDate) p.getParsedValue());
                            default -> cb.equal(root.get(p.getField()), p.getParsedValue());
                        }
                );
                case LIKE -> predicates.add(cb.like(
                        (p.isIgnoreCase()
                                ? cb.lower(root.get(p.getField()))
                                : root.get(p.getField())
                        ),
                        (p.isIgnoreCase()
                                ? "%" + p.getValor().toLowerCase() + "%"
                                : "%" + p.getValor() + "%"))
                );
                case GREATER_THAN -> predicates.add(
                        switch (p.getTipo()) {
                            case DATE -> cb.greaterThan(root.get(p.getField()), (LocalDate) p.getParsedValue());
                            case INTEGER -> cb.greaterThan(root.get(p.getField()), (Integer) p.getParsedValue());
                            case LONG -> cb.greaterThan(root.get(p.getField()), (Long) p.getParsedValue());
                            default -> cb.greaterThan(root.get(p.getField()), p.getValor());
                        }
                );
                case LESS_THAN -> predicates.add(
                        switch (p.getTipo()) {
                            case DATE -> cb.lessThan(root.get(p.getField()), (LocalDate) p.getParsedValue());
                            case INTEGER -> cb.lessThan(root.get(p.getField()), (Integer) p.getParsedValue());
                            case LONG -> cb.lessThan(root.get(p.getField()), (Long) p.getParsedValue());
                            default -> cb.lessThan(root.get(p.getField()), p.getValor());
                        }
                );
                case BETWEEN -> {
                    String[] pares = p.getValor().split(Constantes.BUS_SEPARADOR_BETWEEN);
                    predicates.add(
                            switch (p.getTipo()) {
                                case DATE -> cb.greaterThan(root.get(p.getField()), LocalDate.parse(pares[0]));
                                case INTEGER -> cb.greaterThan(root.get(p.getField()), Integer.decode(pares[0]));
                                case LONG -> cb.greaterThan(root.get(p.getField()), Long.valueOf(pares[0]));
                                default -> cb.greaterThan(root.get(p.getField()), pares[0]);
                            });
                    predicates.add(
                            switch (p.getTipo()) {
                                case DATE -> cb.lessThan(root.get(p.getField()), LocalDate.parse(pares[1]));
                                case INTEGER -> cb.lessThan(root.get(p.getField()), Integer.decode(pares[1]));
                                case LONG -> cb.lessThan(root.get(p.getField()), Long.valueOf(pares[1]));
                                default -> cb.lessThan(root.get(p.getField()), pares[1]);
                            });
                }
            }
        });

        // establezco el orden de la consulta
        page.getSort().forEach(o -> orders.add((o.isAscending()
                ? cb.asc(root.get(o.getProperty()))
                : cb.desc(root.get(o.getProperty())))));

        // configuro la consulta completa
        query.select(root)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(orders);
        TypedQuery<U> result = em.createQuery(query);

        // genero la paginacion
        count.select(cb.count(count.from(clase)));
        count.where(predicates.toArray(new Predicate[0]));

        result.setFirstResult(page.getPageNumber() * page.getPageSize());
        result.setMaxResults(page.getPageSize());

        // retorna la pagina resultado
        return new PageImpl<>(result.getResultList(), page, em.createQuery(count).getSingleResult());
    }
}
