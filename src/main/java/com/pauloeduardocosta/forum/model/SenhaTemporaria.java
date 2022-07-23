package com.pauloeduardocosta.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@Entity
@Table(name = "senhasTemporaris")
public class SenhaTemporaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senha;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NonNull
    private LocalDateTime dataValidade;

    public SenhaTemporaria(String senha, Long validadeEmMinutos) {
        this.senha = senha;
        this.dataValidade = dataCriacao.plus(validadeEmMinutos, ChronoUnit.MINUTES);
    }
}
