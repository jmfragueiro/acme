package ar.com.acme.application.user;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import ar.com.acme.application.phone.IPhoneService;
import ar.com.acme.application.templates.controller.Controller;
import ar.com.acme.commons.Constants;

@RestController
@RequestMapping(Constants.URL_CONTROLLER_USER)
public class UserController extends Controller<User, UUID, UserWebInModel, UserWebOutModel> {
    private final IPhoneService phoneService;
    public UserController(IUserService service, IPhoneService phoneService) {
        super(service);
        this.phoneService = phoneService;
    }

    @Override
    protected UserWebOutModel toWebOutModel(User source) {
        return UserWebOutModel.fromUser(source);
    }

    @Override
    protected User fromWebInModel(UserWebInModel source) {
        return source.toUser((IUserService)getService(), phoneService);
    }
}
