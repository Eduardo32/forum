package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.model.Tag;
import com.pauloeduardocosta.forum.repository.ITagRepository;
import com.pauloeduardocosta.forum.utils.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TagServiceTest {

    @Mock
    private ITagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("Dado que são passadas 3 TAGs novas")
    void verificarTagNovasComTresNovas() {
        List<String> tags = Arrays.asList("Tag 1", "tag 2", "TAG 3");
        List<String> tagsSalvas = tagService.verificarTagNovas(tags);

        verify(tagRepository, times(3))
                .findByNomeIgnoreCase(anyString());
        assertNotNull(tagsSalvas);
        assertEquals(tags.size(), tagsSalvas.size());
        assertEquals(tags.get(0), tagsSalvas.get(0));
        assertEquals(StringUtils.primeiraLetraMaiuscula(tags.get(1)), tagsSalvas.get(1));
        assertEquals(StringUtils.primeiraLetraMaiuscula(tags.get(2)), tagsSalvas.get(2));
    }

    @Test
    @DisplayName("Dado que são passadas 2 TAGs novas e uma já existente")
    void verificarTagNovasComDuasNovasEUmaExistente() {
        List<String> tags = Arrays.asList("Tag 1", "tag 2", "TAG 3");

        when(tagRepository.findByNomeIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(null))
                .thenReturn(Optional.of(new Tag("Tag 2")))
                .thenReturn(Optional.ofNullable(null));
        List<String> tagsSalvas = tagService.verificarTagNovas(tags);

        verify(tagRepository, times(3))
                .findByNomeIgnoreCase(anyString());
        assertNotNull(tagsSalvas);
        assertEquals(tags.size(), tagsSalvas.size());
        assertEquals(StringUtils.primeiraLetraMaiuscula(tags.get(0)), tagsSalvas.get(0));
        assertEquals(StringUtils.primeiraLetraMaiuscula(tags.get(1)), tagsSalvas.get(1));
        assertEquals(StringUtils.primeiraLetraMaiuscula(tags.get(2)), tagsSalvas.get(2));
    }

    @Test
    @DisplayName("Dado que não foram informadas TAGs")
    void verificarTagNovasComDuasNovasEUmaExistente1() {
        List<String> tags = new ArrayList<>();
        List<String> tagsSalvas = tagService.verificarTagNovas(tags);

        verify(tagRepository, times(0))
                .findByNomeIgnoreCase(anyString());
        assertEquals(0, tagsSalvas.size());
    }
}