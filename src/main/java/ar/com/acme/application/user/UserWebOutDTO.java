package ar.com.acme.application.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.com.acme.application.phone.PhoneWebDTO;

public record UserWebOutDTO(UUID id,
                            String name,
                            String email,
                            LocalDateTime created,
                            LocalDateTime modified,
                            Boolean active,
                            String token,
                            LocalDateTime lastLogin,
                            Collection<PhoneWebDTO> phones) {
    public static UserWebOutDTO fromUser(User user) {
        return new UserWebOutDTO(user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getCreated(),
                                user.getModified(),
                                user.getActive(),
                                user.getToken(),
                                user.getLastLogin(),
                                user.getPhones().stream().map(PhoneWebDTO::fromPhone).collect(Collectors.toList()));
    }
 }
