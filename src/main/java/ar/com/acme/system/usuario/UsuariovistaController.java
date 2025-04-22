package ar.com.acme.system.usuario;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.acme.ports.control.Controlador;

@RestController
@RequestMapping("/usuariosvista")
public class UsuariovistaController extends Controlador<Usuariovista, Long> {
    public UsuariovistaController(IUsuariovistaService servicio) {
        super(servicio);
    }
}
