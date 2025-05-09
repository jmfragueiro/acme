package ar.com.acme.application.templates.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.io.Serializable;
import java.util.*;

import ar.com.acme.commons.Constants;
import ar.com.acme.application.templates.entity.IEntity;
import ar.com.acme.application.templates.repository.IRepository;

public abstract class Service<U extends IEntity<TKI>, TKI extends Serializable> implements IService<U, TKI> {
    protected final IRepository<U, TKI> repo;
    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
        this.validate(instancia);

        try {
            return repo.save(instancia);
        } catch (Exception ex) {
            throw new ServiceException(Constants.MSJ_REP_ERR_ATSAVEDATA, ex.toString());
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
                sb.append(constraintViolation.getMessage().concat(Constants.SYS_CAD_MSJ_SEPARATOR));
            }
            throw new ConstraintViolationException(
                    Constants.MSJ_REP_ERR_NOVALIDATE.concat(Constants.SYS_CAD_LOGSEP).concat(sb.toString().trim()),
                    violations);
        }
    }
}
