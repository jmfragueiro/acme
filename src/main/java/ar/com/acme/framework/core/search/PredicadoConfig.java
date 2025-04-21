package ar.com.acme.framework.core.search;

public class PredicadoConfig {
    private EPredicadoOperador op;
    private EPredicadoTipo tipo;
    private boolean ignoreCase;

    public PredicadoConfig(EPredicadoOperador op, EPredicadoTipo tipo, boolean ignoreCase) {
        this.op = op;
        this.tipo = tipo;
        this.ignoreCase = ignoreCase;
    }

    public EPredicadoOperador getOp() {
        return op;
    }

    public EPredicadoTipo getTipo() {
        return tipo;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }
}
