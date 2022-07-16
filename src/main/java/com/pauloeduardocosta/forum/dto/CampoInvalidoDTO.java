package com.pauloeduardocosta.forum.dto;

public class CampoInvalidoDTO {

    private String campo;
    private String erro;

    public CampoInvalidoDTO() {
    }

    public CampoInvalidoDTO(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
