package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.data.UsuarioDetalhes;
import com.pauloeduardocosta.forum.dto.NovoTopicoDTO;
import com.pauloeduardocosta.forum.dto.TopicoCompletoDTO;
import com.pauloeduardocosta.forum.dto.TopicoDTO;
import com.pauloeduardocosta.forum.dto.UsuarioDTO;
import com.pauloeduardocosta.forum.model.Resposta;
import com.pauloeduardocosta.forum.model.Topico;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.model.enums.EStatusTopico;
import com.pauloeduardocosta.forum.repository.ITopicoRepository;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.UsuarioNaoEAutorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TopicoServiceTest {

    @Mock
    private TagService tagService;

    @Mock
    private ITopicoRepository topicoRepository;

    @InjectMocks
    private TopicoService topicoService;

    private List<Usuario> usuarios;
    private List<Topico> topicos;
    private List<Resposta> respostas;

    @BeforeEach
    void setUp() {
        usuarios = new ArrayList<>();
        topicos = new ArrayList<>();
        respostas = new ArrayList<>();

        usuarios.addAll(Arrays.asList(
                Usuario.builder()
                        .id(1L)
                        .nome("Usuario Logado")
                        .username("usuario")
                        .email("usuario@email.com")
                        .senha("123456")
                        .build(),
                Usuario.builder()
                        .id(2L)
                        .nome("Seth Brundle")
                        .username("brundon_seth")
                        .email("SethBrundle@email.com")
                        .senha("123456")
                        .build(),
                Usuario.builder()
                        .id(3L)
                        .nome("Brundle Fly")
                        .username("fly")
                        .email("fly@email.com")
                        .senha("123456")
                        .build(),
                Usuario.builder()
                        .id(4L)
                        .nome("Palhaço")
                        .username("palhaco")
                        .email("palhaco@email.com")
                        .senha("123456")
                        .build()
        ));

        topicos.addAll(Arrays.asList(
                Topico.builder()
                        .id(1L)
                        .titulo("Duvida Spring")
                        .mensagem("Duvida com spring boot")
                        .dataCriacao(LocalDateTime.now())
                        .status(EStatusTopico.NAO_RESPONDIDO)
                        .autor(usuarios.get(1))
                        .tags("Spring Boot, Java")
                        .respostas(new ArrayList<>())
                        .build(),
                Topico.builder()
                        .id(2L)
                        .titulo("Duvida Java")
                        .mensagem("Duvida com Java 11")
                        .dataCriacao(LocalDateTime.now())
                        .status(EStatusTopico.NAO_SOLUCIONADO)
                        .autor(usuarios.get(2))
                        .tags("Java")
                        .respostas(respostas)
                        .build()
        ));

        respostas.addAll(Arrays.asList(
                Resposta.builder()
                        .id(1L)
                        .mensagem("Jatentou fazer...")
                        .topico(topicos.get(1))
                        .dataCriacao(LocalDateTime.now())
                        .autor(usuarios.get(1))
                        .solucao(Boolean.FALSE)
                        .build(),
                Resposta.builder()
                        .id(2L)
                        .mensagem("Não sei. Espero ter ajudado.")
                        .topico(topicos.get(1))
                        .dataCriacao(LocalDateTime.now())
                        .autor(usuarios.get(3))
                        .solucao(Boolean.FALSE)
                        .build()
        ));

        Optional<Usuario> usuarioLogado = Optional.of(usuarios.get(0));
        UsuarioDetalhes usuarioDetalhes = new UsuarioDetalhes(usuarioLogado);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuarioLogado, null, usuarioDetalhes.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Dado que estou buscando todos os topicos cadastrados")
    void buscarTodosTeste() {
        PageRequest paginacao = mockPageable();
        when(topicoRepository.findAll(paginacao)).thenReturn(mockPagina());

        Page<TopicoDTO> topicosDTO = topicoService.buscarTodos(paginacao);

        assertNotNull(topicosDTO);
        assertEquals(topicos.size(), topicosDTO.getSize());
        assertEquals(EStatusTopico.NAO_RESPONDIDO.name(), topicosDTO.toList().get(0).getStatus());
        assertEquals(topicos.get(0).getRespostas().size(), topicosDTO.toList().get(0).getQtdRespostas());
        assertEquals(new UsuarioDTO(topicos.get(0).getAutor()), topicosDTO.toList().get(0).getAutor());
        assertEquals(EStatusTopico.NAO_SOLUCIONADO.name(), topicosDTO.toList().get(1).getStatus());
        assertEquals(topicos.get(1).getRespostas().size(), topicosDTO.toList().get(1).getQtdRespostas());
        assertEquals(new UsuarioDTO(topicos.get(1).getAutor()), topicosDTO.toList().get(1).getAutor());
    }

    @Test
    @DisplayName("Dado que estou pesquisando por um topico com termos especificos")
    void buscarPorMensagemTeste() {
        PageRequest paginacao = mockPageable();
        String pesquisa = "Spring Boot";

        when(topicoRepository.findByMensagemContainingIgnoreCase(pesquisa, paginacao))
                .thenReturn(new PageImpl<>(topicos.stream()
                        .filter(topico -> topico.getMensagem().toLowerCase().contains(pesquisa.toLowerCase()))
                        .collect(Collectors.toList())));

        Page<TopicoDTO> topicosDTO = topicoService.buscarPorCurso(pesquisa, paginacao);

        assertNotNull(topicosDTO);
        assertEquals(1, topicosDTO.getSize());
        assertEquals(EStatusTopico.NAO_RESPONDIDO.name(), topicosDTO.toList().get(0).getStatus());
        assertEquals(topicos.get(0).getRespostas().size(), topicosDTO.toList().get(0).getQtdRespostas());
        assertEquals(new UsuarioDTO(topicos.get(0).getAutor()), topicosDTO.toList().get(0).getAutor());
    }

    @Test
    @DisplayName("Dado que estou pesquisando por um topico com ID especifico e que existe")
    void buscarTopicoPorIdQueExisteTeste() {
        PageRequest paginacao = mockPageable();
        Long id = 2l;

        when(topicoRepository.findById(id))
                .thenReturn(topicos.stream()
                        .filter(topico -> topico.getId().equals(id))
                        .findFirst());

        TopicoCompletoDTO topicoCompletoDTO = topicoService.buscarTopicoPorId(id);

        assertNotNull(topicoCompletoDTO);
        assertEquals(EStatusTopico.NAO_SOLUCIONADO.name(), topicoCompletoDTO.getStatus());
        assertEquals(topicos.get(1).getRespostas().size(), topicoCompletoDTO.getRespostas().size());
        assertEquals(new UsuarioDTO(topicos.get(1).getAutor()), topicoCompletoDTO.getAutor());
    }

    @Test()
    @DisplayName("Dado que estou pesquisando por um topico com ID especifico e que não existe")
    void buscarTopicoPorIdQueNaoExisteTeste() {
        PageRequest paginacao = mockPageable();
        Long id = 9999999l;

        when(topicoRepository.findById(id))
                .thenReturn(topicos.stream()
                        .filter(topico -> topico.getId().equals(id))
                        .findFirst());

        assertThrows(ObjetoNaoEncotradoException.class, () -> topicoService.buscarTopicoPorId(id));
    }

    @Test
    @DisplayName("Dado que está sendo criado um novo topico")
    void criarTopicoTeste() {
        NovoTopicoDTO novoTopicoDTO = mockNovoTopicoDTO();
        when(tagService.verificarTagNovas(anyList())).thenReturn(novoTopicoDTO.getTags());

        TopicoCompletoDTO topicoCompletoDTO = topicoService.criarTopico(novoTopicoDTO);

        verify(topicoRepository, times(1)).save(any(Topico.class));
        assertNotNull(topicoCompletoDTO);
        assertEquals(new UsuarioDTO(usuarios.get(0)), topicoCompletoDTO.getAutor());
        assertEquals(EStatusTopico.NAO_RESPONDIDO.name(), topicoCompletoDTO.getStatus());
        assertEquals(0, topicoCompletoDTO.getRespostas().size());
    }

    @Test
    @DisplayName("Dado que sou o autor do topico e estou marcando como solucionado")
    void marcarTopicoComoSolucionadoTeste() {
        topicos.add(Topico.builder()
                .id(3L)
                .titulo("Duvida do Usuario Logado")
                .mensagem("Esse topico foi criado pelo usuario logado")
                .dataCriacao(LocalDateTime.now())
                .status(EStatusTopico.NAO_SOLUCIONADO)
                .autor(usuarios.get(0))
                .tags("Java")
                .respostas(respostas)
                .build());
        Long topicoId = 3L;
        Long respostaId = 1L;

        when(topicoRepository.findById(topicoId))
                .thenReturn(topicos.stream()
                        .filter(topico -> topico.getId().equals(topicoId))
                        .findFirst());

        TopicoCompletoDTO topicoCompletoDTO = topicoService.marcarTopicoComoSolucionado(topicoId, respostaId);

        assertNotNull(topicoCompletoDTO);
        assertEquals(EStatusTopico.SOLUCIONADO.name(), topicoCompletoDTO.getStatus());
        assertEquals(Boolean.TRUE, topicos.get(2).getRespostas().get(0).getSolucao());
    }

    @Test
    @DisplayName("Dado que não sou o autor do topico e estou marcando como solucionado")
    void marcarTopicoComoSolucionadoNaoSendoAutorTeste() {
        topicos.add(Topico.builder()
                .id(3L)
                .titulo("Duvida do Usuario Logado")
                .mensagem("Esse topico foi criado pelo usuario logado")
                .dataCriacao(LocalDateTime.now())
                .status(EStatusTopico.NAO_SOLUCIONADO)
                .autor(usuarios.get(1))
                .tags("Java")
                .respostas(respostas)
                .build());
        Long topicoId = 3L;
        Long respostaId = 1L;

        when(topicoRepository.findById(topicoId))
                .thenReturn(topicos.stream()
                        .filter(topico -> topico.getId().equals(topicoId))
                        .findFirst());

        assertThrows(UsuarioNaoEAutorException.class,
                () -> topicoService.marcarTopicoComoSolucionado(topicoId, respostaId));
    }

    @Test
    @DisplayName("Dado que sou o autor do topico e o topico não tem respostas")
    void marcarTopicoComoSolucionadoNaoTendoRespostaTeste() {
        topicos.add(Topico.builder()
                .id(3L)
                .titulo("Duvida do Usuario Logado")
                .mensagem("Esse topico foi criado pelo usuario logado")
                .dataCriacao(LocalDateTime.now())
                .status(EStatusTopico.NAO_RESPONDIDO)
                .autor(usuarios.get(0))
                .tags("Java")
                .respostas(respostas)
                .build());
        Long topicoId = 3L;
        Long respostaId = 99999999L;

        when(topicoRepository.findById(topicoId))
                .thenReturn(topicos.stream()
                        .filter(topico -> topico.getId().equals(topicoId))
                        .findFirst());

        assertThrows(ObjetoNaoEncotradoException.class,
                () -> topicoService.marcarTopicoComoSolucionado(topicoId, respostaId));
    }

    private NovoTopicoDTO mockNovoTopicoDTO() {
        return new NovoTopicoDTO(
                "Titulo Teste",
                "Mensagem teste",
                Arrays.asList("Tag 1", "Tag 2", "Tag 3"));
    }

    private PageRequest mockPageable() {
        return PageRequest.of(0, 10, Sort.by("dataCriacao").descending());
    }

    private Page<Topico> mockPagina() {
        return new PageImpl<>(topicos);
    }
}