package com.pauloeduardocosta.forum.model;

import com.pauloeduardocosta.forum.model.enums.EStatusTopico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "topicos")
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String titulo;

	@NonNull
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	private EStatusTopico status = EStatusTopico.NAO_RESPONDIDO;

	@ManyToOne
	@NonNull
	private Usuario autor;

	@OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE)
	private List<Resposta> respostas = new ArrayList<>();
}
