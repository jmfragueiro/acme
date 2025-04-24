package ar.com.acme.application.user;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.adapter.common.AdapterConstants;
import ar.com.acme.adapter.controller.Controller;

@RestController
@RequestMapping("/usuarios")
public class UserController extends Controller<User, UUID, UserWVO> {
    public UserController(IUserService service) {
        super(service);
    }

    @Override
    protected UserWVO toWebModel(User source) {
        return UserWVO.fromUser(source);
    }

    @Override
    protected User toAppModel(UserWVO source) {
        User user;

        if (source == null) {
            return null;
        }

        if (source.id() != null) {
            user = ((IUserService) getService()).findById(source.id()).orElseThrow(() -> new UserException(AdapterConstants.MSJ_REP_ERR_NOITEM, "User"));
        } else {
            user = new User();
        }

        user.setName(source.name());
        user.setEmail(source.email());
        user.setPassword(source.password());
        user.setLastLogin(source.lastLogin());
        user.setActive(source.active());
        user.setToken(source.token());
        source.phones().stream().map(PhoneWVO::fromPhone).collect(Collectors.toList()));
    }
}
