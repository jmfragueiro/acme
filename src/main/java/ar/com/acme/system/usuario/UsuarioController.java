package ar.com.acme.system.usuario;

import ar.com.acme.adapter.controller.Controller;
import org.springframework.web.bind.annotation.*;

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
    protected Usuario toEntityModel(UsuarioWVO source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntityModel'");
    }
}
