package br.edu.ufcg.les142.models;

/**
 * Created by nicodemos on 26/02/15.
 */
public enum TipoRelato {
    LUZ ("Luz"),
    AGUA ("Agua"),
    ESTRADA ("Estrada"),
    GERAL ("Geral");

    private String nome;

    TipoRelato(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public static TipoRelato parse(String nome) {
        for (TipoRelato s : TipoRelato.values()) {
            if (s.toString().equals(nome)) {
                return s;
            }
        }
        return TipoRelato.GERAL;
    }
}
