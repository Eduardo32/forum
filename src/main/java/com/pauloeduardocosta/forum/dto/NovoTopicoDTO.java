package com.pauloeduardocosta.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class NovoTopicoDTO {

    @NotBlank
    @Length(min = 5)
    private String titulo;

    @NotBlank
    @Length(min = 10)
    private String mensagem;
}
