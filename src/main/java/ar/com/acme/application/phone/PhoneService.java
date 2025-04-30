package ar.com.acme.application.phone;

import org.springframework.stereotype.Service;

import jakarta.validation.Validator;

import java.util.UUID;

@Service
public class PhoneService extends ar.com.acme.base.templates.service.Service<Phone, UUID> implements IPhoneService {
    public PhoneService(IPhoneRepo phoneRepo, Validator validator) {
        super(phoneRepo, validator);
    }
}
