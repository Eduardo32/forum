package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.model.Tag;
import com.pauloeduardocosta.forum.repository.ITagRepository;
import com.pauloeduardocosta.forum.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private ITagRepository tagRepository;

    public List<String> verificarTagNovas(List<String> tags) {
        List<String> tagsSalvas = new ArrayList<>();
        if(tags.size() != 0) {
            tags.forEach(tag -> {
                Optional<Tag> tagSalva = tagRepository.findByNomeIgnoreCase(tag);
                if (tagSalva.isPresent()) {
                    tagsSalvas.add(tagSalva.get().getNome());
                } else {
                    Tag novaTag = new Tag();
                    novaTag.setNome(StringUtils.primeiraLetraMaiuscula(tag));
                    tagRepository.save(novaTag);
                    tagsSalvas.add(novaTag.getNome());
                }
            });
        }
        return tagsSalvas;
    }
}
