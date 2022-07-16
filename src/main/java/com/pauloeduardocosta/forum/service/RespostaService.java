package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.NovaRespostaDTO;
import com.pauloeduardocosta.forum.dto.RespostaDTO;
import com.pauloeduardocosta.forum.model.Resposta;
import com.pauloeduardocosta.forum.model.Topico;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.model.enums.EStatusTopico;
import com.pauloeduardocosta.forum.repository.IRespostaRepository;
import com.pauloeduardocosta.forum.repository.ITopicoRepository;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.TopicoFechadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private IRespostaRepository respostaRepository;

    public RespostaDTO criarResposta(NovaRespostaDTO novaRespostaDTO) {
        //TODO: devera pegar o usuario logado
        Optional<Usuario> autor = usuarioRepository.findById(novaRespostaDTO.getAutorId());
        Optional<Topico> topico = topicoRepository.findById(novaRespostaDTO.getTopicoId());
        if(!topico.isPresent()) {
            throw new ObjetoNaoEncotradoException("Topico com id " + novaRespostaDTO.getTopicoId() + " não encontrado.");
        }
        if(topico.get().getStatus() == EStatusTopico.FECHADO) {
            throw new TopicoFechadoException("O topico id " + topico.get().getId() + " já está fechado e não pode receber mais respostas.");
        }
        if(topico.get().getStatus() == EStatusTopico.NAO_RESPONDIDO) {
            topico.get().setStatus(EStatusTopico.NAO_SOLUCIONADO);
            topicoRepository.save(topico.get());
        }
        Resposta resposta = new Resposta(novaRespostaDTO.getMensagem(), topico.get(), autor.get());
        respostaRepository.save(resposta);
        return new RespostaDTO(resposta);
    }
}
