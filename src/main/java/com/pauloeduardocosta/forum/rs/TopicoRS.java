package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.AtualizarTopicoDTO;
import com.pauloeduardocosta.forum.dto.NovoTopicoDTO;
import com.pauloeduardocosta.forum.dto.TopicoCompletoDTO;
import com.pauloeduardocosta.forum.dto.TopicoDTO;
import com.pauloeduardocosta.forum.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/topicos")
public class TopicoRS {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public Page<TopicoDTO> getTopicos(@RequestParam(required = false) String mensagem,
                                      @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable paginacao) {
        if(mensagem == null) {
            return topicoService.buscarTodos(paginacao);
        }
        return topicoService.buscarPorCurso(mensagem, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoCompletoDTO> getTopicoCompleto(@PathVariable Long id) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.buscarTopicoPorId(id);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @PostMapping
    public ResponseEntity<TopicoCompletoDTO> criarTopico(@Validated @RequestBody NovoTopicoDTO novoTopicoDTO, UriComponentsBuilder uriBuilder) {
        TopicoCompletoDTO topico = topicoService.criarTopico(novoTopicoDTO);

        URI uri = uriBuilder.path("/api/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoCompletoDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarTopicoDTO atualizarTopicoDTO) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.atualizarTopico(id, atualizarTopicoDTO);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @PutMapping("/{topicoId}/solucionado/{respostaId}")
    public ResponseEntity<TopicoCompletoDTO> marcarTopicoComoSolucionado(@PathVariable Long topicoId, @PathVariable Long respostaId) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.marcarTopicoComoSolucionado(topicoId, respostaId);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @PutMapping("/{topicoId}/nao-solucionado")
    public ResponseEntity<TopicoCompletoDTO> marcarTopicoComoNaoSolucionado(@PathVariable Long topicoId) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.marcarTopicoComoNaoSolucionado(topicoId);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @PutMapping("/{topicoId}/fechado")
    public ResponseEntity<TopicoCompletoDTO> fecharTopico(@PathVariable Long topicoId) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.fecharTopico(topicoId);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @PutMapping("/{topicoId}/reabrir")
    public ResponseEntity<TopicoCompletoDTO> reabrirTopico(@PathVariable Long topicoId) {
        TopicoCompletoDTO topicoCompletoDTO = topicoService.reabrirTopico(topicoId);
        return ResponseEntity.ok().body(topicoCompletoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deletarTopico(@PathVariable Long id) {
        topicoService.excluirTopico(id);
        return ResponseEntity.ok().build();
    }
}
