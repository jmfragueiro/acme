package ar.com.acme.base.templates.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.io.Serializable;
import java.util.*;

import ar.com.acme.base.common.BaseConstants;
import ar.com.acme.base.templates.entity.IEntity;
import ar.com.acme.base.templates.repository.IRepository;

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
    protected final Validator validator;

    @PersistenceContext
    protected EntityManager entityManagerrrr;

    protected Service(IRepository<U, TKI> repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }

    public IRepository<U, TKI> getRepo() {
        return repo;
    }

    @Override
    public U persist(U instancia) throws ServiceException {
        this.validate(instancia);
        
        try {
            return repo.save(instancia);
        } catch (Exception ex) {
            throw new ServiceException(BaseConstants.MSJ_REP_ERR_ATSAVEDATA, ex.toString());
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

    protected void validate(U entity) {
        Set<ConstraintViolation<U>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<U> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException(BaseConstants.MSJ_REP_ERR_NOVALIDATE + BaseConstants.SYS_CAD_LOGSEP + sb.toString(), violations);
        }
    }
}
