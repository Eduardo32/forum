package com.pauloeduardocosta.forum.model.enums;

public enum ETipoToken {
    BEARER(1, "Bearer");

    private int cod;
    private String descricao;

    ETipoToken(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
}
