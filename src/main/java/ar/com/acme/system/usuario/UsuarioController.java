package ar.com.acme.system.usuario;

import org.springframework.web.bind.annotation.*;

import ar.com.acme.template.controller.Controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends Controller<Usuario, Long, UsuarioWVO> {
    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioController(IUsuarioRepo repo) {
        super(repo);
    }

    @Override
    protected UsuarioWVO toWebModel(Usuario source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toWebModel'");
    }

    @Override
    protected Usuario toAppModel(UsuarioWVO source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntityModel'");
    }
}
