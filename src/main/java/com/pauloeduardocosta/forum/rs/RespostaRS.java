package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.AtualizarRespostaDTO;
import com.pauloeduardocosta.forum.dto.AtualizarTopicoDTO;
import com.pauloeduardocosta.forum.dto.NovaRespostaDTO;
import com.pauloeduardocosta.forum.dto.RespostaDTO;
import com.pauloeduardocosta.forum.dto.TopicoCompletoDTO;
import com.pauloeduardocosta.forum.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/respostas")
public class RespostaRS {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<RespostaDTO> responderTopico(@Validated @RequestBody NovaRespostaDTO novaRespostaDTO) {
        RespostaDTO respostaDTO = respostaService.criarResposta(novaRespostaDTO);
        return ResponseEntity.created(null).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaDTO> atualizarTopico(@PathVariable Long id, @RequestBody AtualizarRespostaDTO atualizarTopicoDTO) {
        RespostaDTO respostaDTO = respostaService.atualizarTopico(id, atualizarTopicoDTO);
        return ResponseEntity.ok().body(respostaDTO);
    }
}
