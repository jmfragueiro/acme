package ar.gov.posadas.mbe.framework.core.exception;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Tools;

public class TooManyRowsException extends MessageException {
    public TooManyRowsException(String entidad, String item) {
        super(Constantes.MSJ_ERR_REPO_TOOMANY
                .concat(Constantes.SYS_CAD_SPACE)
                .concat(entidad)
                .concat(Constantes.SYS_CAD_OPENTYPE)
                .concat(item)
                .concat(Constantes.SYS_CAD_CLOSETPE),
                Tools.getNombreMetodoLlamante(2));
    }
}
