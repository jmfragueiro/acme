package ar.com.acme.ports.service;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Response;
import ar.com.acme.framework.common.Tools;
import ar.com.acme.ports.entity.EntityException;
import ar.com.acme.ports.entity.IEntidad;
import ar.com.acme.ports.entity.IEntityContext;
import ar.com.acme.ports.repos.IRepositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Implementación de interfase IServicio para un sistema con JPA-Spring. Esta clase es ademas
 * la que se encuentra encargada de representar al EntityManager que se usa en la aplicacion
 * para el manejo de la persistencia contra una base de datos relacional.
 *
 * @param <U>   El tipo de la entidad servida por el servicio
 * @param <TKI> El tipo de la clave de identificacion para la entidad
 * @author jmfragueiro
 * @version 20200201
 */
public abstract class Servicio<U extends IEntidad<TKI>, TKI extends Serializable> implements IServicio<U, TKI> {
    protected final IRepositorio<U, TKI> repo;

    @PersistenceContext
    protected EntityManager entityManager;

    protected Servicio(IRepositorio<U, TKI> repo) {
        this.repo = repo;
    }

    protected IRepositorio<U, TKI> getRepo() {
        return repo;
    }

    protected IEntityContext getEntityContext() {
        return (IEntityContext)entityManager;
    }

    @Override
    public String getNombreEntidadAsociada() {
        return Tools.getNombreClaseFormateado(this.getClass(), "service");
    }

    @Override
    public U persist(U instancia) throws ServiceException {
        try {
            /* No borrar la llamada a normalizarDatos, mas alla que tambien se realiza desde el controlador */
            instancia = this.normalizarDatos(instancia);
            instancia.onPersist();
            repo.save(instancia);

            // volver a leer el pbjeto persistido y devolver ese nuevo
            return instancia;
        } catch (Exception ex) {
            throw new ServiceException(
                    Tools.getExcFormatedMesg(Constantes.MSJ_ERR_ATSAVEDATA, getNombreEntidadAsociada()),
                    Tools.getMesgSQLException(ex).concat(Constantes.SYS_CAD_LOGSEP).concat(Constantes.SYS_CAD_SPACE).concat(instancia.toString()));
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public U refresh(U instancia) throws ServiceException {
        try {
            getEntityContext().detach(instancia);
            U refreshinstancia = findById(instancia.getId()).get();
            return refreshinstancia;
        } catch (Exception ex) {
            throw new ServiceException(
                    Tools.getExcFormatedMesg(Constantes.MSJ_ERR_ATREFRESHDATA, getNombreEntidadAsociada()),
                    Tools.getMesgSQLException(ex).concat(Constantes.SYS_CAD_LOGSEP).concat(Constantes.SYS_CAD_SPACE).concat(instancia.toString()));
        }
    }

    @Override
    public void delete(U instancia) throws ServiceException {
        instancia.onDelete();
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

    @Override
    public Long countAllAlive() throws EntityException {
        return repo.countAllAlive();
    }

    @Override
    public U normalizarDatos(U instancia) {
        return instancia;
    }

    @Override
    public Response prePersist(U instancia, U instanciaoriginal, String metodo) {
        return Response.ok();
    }

    @Override
    public void postPersist(U instancia, U instanciaoriginal, String metodo) throws IOException {
    }

    @Override
    public Page<U> findGeneral(String filtro, Pageable pageable) {
        /*
         Como Regla General las Opciones de Busqueda en el parametro recibido "filtro"
         puede adoptar dos alternativas
         1 - Se recibe un unico dato, que por lo general es enviado por las busquedas
              rapidas de cada modulo del frontend, dato que será evaluado para hacer de la
              busqueda rapida, una inteligente de acuerdo a los parametros de evaluación
         2-  Se recibe varios datos de busqueda, pero esta vez agrupados de a pares, con el
              formato "clave,valor"
              El parametro clave tendra el campo por el cual buscar y el parametro valor
              el conteniido de la busqueda.
              Entre cada "clave,valor" habra un caracter identificador de separacion entre
              cada pares de datos. Este metodo de envio de datos lo realiza la utilidad
              de busqueda avanzada del frontend.
         Entonces como primera medida hay que identificar que metodo de busqueda se debe
         resolver, para luego si, enviar el proceso a los metodos correspondientes del service.
        */
        if (filtro.length() > 0) {
            String[] pares = filtro.split(Constantes.BUS_SEPARADOR_PARES);
            String[] individual = pares[0].split(Constantes.BUS_SEPARADOR_CLAVE_VALOR);

            if (individual.length == 1) {
                return this.findParametroUnico(individual[0], pageable);
            } else {
                return this.findAvanzada(filtro, pageable);
            }
        } else {
            return this.findParametroUnico(filtro, pageable);
        }
    }

    protected Page<U> findParametroUnico(String filtro, Pageable pageable) {
        return this.getRepo().findAllAlive(pageable);
    }

    protected Page<U> findAvanzada(String filtro, Pageable pageable) {
        return this.findParametroUnico(filtro, pageable);
    }
}
