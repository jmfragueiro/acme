package ar.com.acme.ports.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public interface IEntityContext extends EntityManager { }
