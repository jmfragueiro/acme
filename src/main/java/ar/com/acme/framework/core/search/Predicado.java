package ar.gov.posadas.mbe.framework.core.search;

import ar.gov.posadas.mbe.framework.common.Constantes;

import java.time.LocalDate;

public class Predicado {
    private String label;
    private String field;
    private EPredicadoOperador op;
    private EPredicadoTipo tipo;
    private String valor;
    private boolean ignoreCase;

    public Predicado(String label, String field, EPredicadoOperador op, EPredicadoTipo tipo, String valor, boolean ignoreCase) {
        this.label = label;
        this.field = field;
        this.op = op;
        this.tipo = tipo;
        this.valor = valor;
        this.ignoreCase = ignoreCase;
    }

    public Predicado(String label, EPredicadoOperador op, EPredicadoTipo tipo, String valor, boolean ignoreCase) {
        this(label, label, op, tipo, valor, ignoreCase);
    }

    public Predicado(String label, PredicadoConfig cfg, String valor) {
        this(label, label, cfg.getOp(), cfg.getTipo(), valor, cfg.isIgnoreCase());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public EPredicadoOperador getOp() {
        return op;
    }

    public void setOp(EPredicadoOperador op) {
        this.op = op;
    }

    public EPredicadoTipo getTipo() {
        return tipo;
    }

    public void setTipo(EPredicadoTipo tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public Object getParsedValue() {
        return switch (tipo) {
            case DATE -> LocalDate.parse(valor);
            case INTEGER -> Integer.decode(valor);
            case LONG -> Long.valueOf(valor);
            case BOOLEAN -> (valor.toLowerCase().equals(Constantes.SYS_CAD_VERDADERO) ? 1 : 0);
            //case OBJECT -> ?????;
            default -> valor;
        };
    }
}
