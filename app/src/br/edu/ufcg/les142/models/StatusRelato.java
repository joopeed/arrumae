package br.edu.ufcg.les142.models;

/**
 * Created by Leticia on 23/01/2015.
 */
public enum StatusRelato {
    ABERTO ("Aberto"),
    EM_PROCESSO("Em processo de resolução"),
    RESOlVIDO ("Resolvido");

    private String nome;

    StatusRelato(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
       return this.nome;
    }

    public static StatusRelato parse(String nome) {
        for (StatusRelato s : StatusRelato.values()) {
            if (s.toString().equals(nome)) {
                return s;
            }
        }
        return StatusRelato.ABERTO;
    }
}

