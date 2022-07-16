package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.NovaRespostaDTO;
import com.pauloeduardocosta.forum.dto.RespostaDTO;
import com.pauloeduardocosta.forum.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/respostas")
public class RespostaRS {

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity<RespostaDTO> responderTopico(@Validated @RequestBody NovaRespostaDTO novaRespostaDTO, UriComponentsBuilder uriBuilder) {
        RespostaDTO respostaDTO = respostaService.criarResposta(novaRespostaDTO);
        return ResponseEntity.created(null).body(respostaDTO);
    }
}
