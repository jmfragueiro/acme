package ar.com.acme.application.phone;

import java.util.UUID;

public record PhoneWVO(UUID id, Long number, Integer citycode, Integer countrycode) {
    public static PhoneWVO fromPhone(Phone phone) {
        return new PhoneWVO(phone.getId(), phone.getNumber(), phone.getCitycode(), phone.getCountrycode());
    }
 }
