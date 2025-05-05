package ar.com.acme.application.phone;

import org.springframework.stereotype.Repository;

import ar.com.acme.base.templates.repository.IRepository;

import java.util.UUID;

@Repository
public interface IPhoneRepo extends IRepository<Phone, UUID> { }
