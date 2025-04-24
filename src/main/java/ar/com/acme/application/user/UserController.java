package ar.com.acme.application.user;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.adapter.controller.Controller;
import ar.com.acme.application.phone.IPhoneService;

@RestController
@RequestMapping("/usuarios")
public class UserController extends Controller<User, UUID, UserWebInDTO, UserWebOutDTO> {
    private final IPhoneService phoneService;
    public UserController(IUserService service, IPhoneService phoneService) {
        super(service);
        this.phoneService = phoneService;
    }

    @Override
    protected UserWebOutDTO toWebOutModel(User source) {
        return UserWebOutDTO.fromUser(source);
    }

    @Override
    protected User fromWebInModel(UserWebInDTO source) {
        return source.toUser((IUserService)getService(), phoneService);
    }
}
