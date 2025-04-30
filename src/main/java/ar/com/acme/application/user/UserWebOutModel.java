package ar.com.acme.application.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.PhoneWebDTO;

public record UserWebOutModel(UUID id,
                            String name,
                            String email,
                            LocalDateTime created,
                            LocalDateTime modified,
                            Boolean active,
                            String token,
                            LocalDateTime lastLogin,
                            Collection<PhoneWebDTO> phones) {
    public static UserWebOutModel fromUser(User user) {
        return new UserWebOutModel(user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getCreated(),
                                user.getModified(),
                                user.getActive(),
                                (user.getToken() != null ? user.getToken().toString() : null),
                                user.getLastLogin(),
                                user.getPhones().stream().map(PhoneWebDTO::fromPhone).collect(Collectors.toList()));
    }
 }
