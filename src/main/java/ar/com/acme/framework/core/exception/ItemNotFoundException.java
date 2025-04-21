package ar.com.acme.framework.core.exception;

import ar.com.acme.framework.common.Constantes;
import ar.com.acme.framework.common.Tools;

public class ItemNotFoundException extends MessageException {
    public ItemNotFoundException(String entidad, String item) {
        super(Constantes.MSJ_ERR_REPO_NOITEM
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(entidad)
                .concat(Constantes.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constantes.SYS_CAD_CLOSETPE),
                Tools.getNombreMetodoLlamante(2));
    }
}
