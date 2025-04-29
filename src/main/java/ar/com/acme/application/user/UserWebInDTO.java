package ar.com.acme.application.user;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.application.phone.PhoneWebDTO;
import ar.com.acme.base.common.BaseConstants;
import ar.com.acme.base.common.Encoder;

public record UserWebInDTO(UUID id,
                           String name,
                           String email,
                           String password, // null si no hay que modificar
                           Boolean active,
                           Collection<PhoneWebDTO> phones) {
    public User toUser(IUserService service, IPhoneService phoneService) {
        User user;

        if (this.id != null) {
            user = service.findById(this.id())
                          .orElseThrow(() -> new UserException(BaseConstants.MSJ_REP_ERR_NOITEM, "User"));
        } else {
            user = new User();
        }

        // si no viene en nulo entonces se modifica el password
        if (this.password() != null) {
            if (!service.isValidPassword(this.password())) {
                throw new UserException(User.ERR_BAD_PASSWORD, this.password());
            }
            user.setPassword(Encoder.encodePassword(this.password()));
        }

        if (!service.isValidEmail(this.email())) {
            throw new UserException(User.ERR_BAD_EMAIL, this.email());
        }
        user.setEmail(this.email());

        user.setName(this.name());
        user.setActive(this.active());
        this.phones().stream().map(p -> p.toPhone(phoneService)).collect(Collectors.toList());

        return user;
    }
 }
