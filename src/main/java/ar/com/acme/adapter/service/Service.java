package ar.com.acme.adapter.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.*;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.adapter.entity.IEntity;
import ar.com.acme.adapter.repository.IRepository;

/**
 * Implementación de interfase IServicio para un sistema con JPA-Spring. Esta clase es ademas
 * la que se encuentra encargada de representar al EntityManager que se usa en la aplicacion
 * para el manejo de la persistencia contra una base de datos relacional.
 *
 * @param <U>   El tipo de la entidad servida por el servicio
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20250421
 */
public abstract class Service<U extends IEntity<TKI>, TKI extends Serializable> implements IService<U, TKI> {
    protected final IRepository<U, TKI> repo;

    @PersistenceContext
    protected EntityManager entityManagerrrr;

    protected Service(IRepository<U, TKI> repo) {
        this.repo = repo;
    }

    public IRepository<U, TKI> getRepo() {
        return repo;
    }

    @Override
    public U persist(U instancia) throws ServiceException {
        try {
            return repo.save(instancia);
        } catch (Exception ex) {
            throw new ServiceException(AdapterConstants.MSJ_REP_ERR_ATSAVEDATA, instancia.toString());
        }
    }

    @Override
    public void delete(U instancia) throws ServiceException {
        instancia.kill();
        persist(instancia);
    }

    @Override
    public Optional<U> findById(TKI id) {
        return repo.findById(id);
    }

    @Override
    public List<U> findAllAlive() {
        return repo.findAllAlive();
    }
}
