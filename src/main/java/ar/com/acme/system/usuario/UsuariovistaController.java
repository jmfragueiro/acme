package ar.com.acme.system.usuario;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.adapter.controller.Controller;

@RestController
@RequestMapping("/usuariosvista")
public class UsuariovistaController extends Controller<Usuariovista, Long> {
    public UsuariovistaController(IUsuariovistaService servicio) {
        super(servicio);
    }
}
