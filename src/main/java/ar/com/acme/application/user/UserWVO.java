package ar.com.acme.application.user;

public record UserWVO(String user, String password, String nombre, String email, Collection<PhoneWVO> phones) {
    public static from(User user) {
        return new UserWVO(user.getUser(),
                           user.getPassword(),
                           user.getNombre(),
                           user.getEmail(),
                           user.getPhones().stream().map(PhoneWVO::from).collect(Collectors.toList()));
    }

    public User toUser() {
        return new User(user, password, nombre, email, phones.stream().map(PhoneWVO::toPhone).collect(Collectors.toList()));
    }
 }
