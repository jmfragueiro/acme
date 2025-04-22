package ar.com.acme.system.usuario;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Logging;
import ar.com.acme.framework.common.Numeros;
import ar.com.acme.framework.core.extradata.IExtraDataService;
import ar.com.acme.ports.service.Servicio;
import ar.com.acme.impldefault.DefaultToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UsuariovistaService extends Servicio<Usuariovista, Long> implements IUsuariovistaService, IExtraDataService {
    private final ObjectMapper om = new ObjectMapper();

    public UsuariovistaService(IUsuariovistaRepo repo) {
        super(repo);
        om.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Page<Usuariovista> findUsuarios(String filtro, Pageable pageable) {
        if (Numeros.esNumerico(filtro) && !filtro.equals("")) {
            Integer documento = Integer.parseInt(filtro);
            return ((IUsuariovistaRepo) getRepo()).findUsuariosByDocumento(documento, pageable);
        } else {
            return ((IUsuariovistaRepo) getRepo()).findUsuariosByUsername(filtro.toLowerCase(), pageable);
        }
    }

    @Override
    public Usuariovista findByUsername(String username) {
        return ((IUsuariovistaRepo) getRepo()).findByUsername(username);
    }

    @Override
    public Optional<String> getUsernameByUsuarioId(Long id) {
        return ((IUsuariovistaRepo) getRepo()).getUsernameByUsuarioId(id);
    }

    @Override
    public Page<Usuariovista> findGeneral(String filtro, Pageable pageable) {
        return this.findUsuarios(filtro, pageable);
    }

    @Override
    public Object create(Object origen) {
        return findByUsername(((DefaultToken) origen).getPayload().getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object extract(Object data) {
        try {
            HashMap<String, String> mapa = (HashMap<String, String>) data;
            return om.readValue(om.writeValueAsString(mapa), Usuariovista.class);
        } catch(JsonProcessingException jpe) {
            Logging.info(this.getClass(), Constantes.MSJ_SEC_ERR_CANTEXTRACTTED);
            return null;
        }
    }
}
