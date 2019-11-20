package ifsp.poo.model;

public enum ActivityStatus {
    CORRECTED("Corrigida"),
    WAITING_CORRECTION("Aguardando Correção");

    private String value;

    ActivityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
