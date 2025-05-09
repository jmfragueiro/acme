package ar.com.acme.application.user;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.application.phone.PhoneWebInModel;
import ar.com.acme.commons.Constants;

public record UserWebInModel(UUID id,
                             String name,
                             String email,
                             String password, // null si no hay que modificar
                             Boolean active,
                             Collection<PhoneWebInModel> phones) {
    public User toUser(IUserService service, IPhoneService phoneService) {
        User user;

        // si viene con id entonces busca el usuario para tomar valores
        if (this.id != null) {
            user = service.findById(this.id())
                          .orElseThrow(() -> new UserException(Constants.MSJ_REP_ERR_NOITEM, "User"));
        } else {
            user = new User();
        }

        // si no viene nulo el name, entonces lo carga/modifica
        if (this.name() != null) {
            user.setName(this.name());
        }

        // si no viene en nulo el pass, entonces se valida y carga/modifica
        if (this.password() != null) {
            if (!service.isValidPassword(this.password())) {
                throw new UserException(User.ERR_BAD_PASSWORD, this.password());
            }
            user.setPassword(service.encodePassword(this.password()));
        }

        // el email es obligatorio: un email nulo no deberia pasar la validacion
        if (!service.isValidEmail(this.email())) {
            throw new UserException(User.ERR_BAD_EMAIL, this.email());
        }
        user.setEmail(this.email());

        // al crearse el usuario se activa el mismo, si no, toma lo que viene
        if (user.isNew()) {
            user.setActive(true);
        } else if (this.active() != null) {
            user.setActive(this.active());
        }

        // si trae algo de telefonos, entonces actualiza la lista
        if (!this.phones().isEmpty()) {
            user.setPhones(this.phones()
                               .stream()
                               .map(p -> p.toPhone(phoneService))
                               .peek(phone -> phone.setUser(user))
                               .collect(Collectors.toSet()));
        }

        return user;
    }
 }
