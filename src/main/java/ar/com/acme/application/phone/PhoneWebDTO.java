package ar.com.acme.application.phone;

import java.util.UUID;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.application.user.UserException;

public record PhoneWebDTO(UUID id, Long number, Integer citycode, Integer countrycode) {
    public static PhoneWebDTO fromPhone(Phone phone) {
        return new PhoneWebDTO(phone.getId(), phone.getNumber(), phone.getCitycode(), phone.getCountrycode());
    }

    public Phone toPhone(IPhoneService service) {
        Phone phone;

        if (this.id != null) {
            phone = service.findById(this.id())
                           .orElseThrow(() -> new UserException(AdapterConstants.MSJ_REP_ERR_NOITEM, "Phone"));
        } else {
            phone = new Phone();
        }

        phone.setNumber(this.number);
        phone.setCitycode(this.citycode);
        phone.setCountrycode(this.countrycode);

        return phone;
    }
 }
