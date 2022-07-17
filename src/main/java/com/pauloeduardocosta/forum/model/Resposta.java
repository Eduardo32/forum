package com.pauloeduardocosta.forum.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "respostas")
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String mensagem;

	@ManyToOne
	@NonNull
	private Topico topico;
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@ManyToOne
	@NonNull
	private Usuario autor;
	private Boolean solucao = false;
}
