package ar.com.acme.application.user;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.adapter.controller.Controller;

@RestController
@RequestMapping("/usuarios")
public class UserController extends Controller<User, UUID, UserWVO> {
    public UserController(IUserService service) {
        super(service);
    }

    @Override
    protected UserWVO toWebModel(User source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toWebModel'");
    }

    @Override
    protected User toAppModel(UserWVO source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntityModel'");
    }
}
