package ar.gov.posadas.mbe.ports.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public interface IEntityContext extends EntityManager { }
