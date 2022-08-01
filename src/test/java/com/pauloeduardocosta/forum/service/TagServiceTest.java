package com.pauloeduardocosta.forum.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    void verificarTagNovas() {
        List<String> tags = Arrays.asList("tAg 1", "Tag 2", "TAG 3");
        List<String> tagsSalvas = tagService.verificarTagNovas(tags);

        assertNotNull(tagsSalvas);
        assertEquals(tags.size(), tagsSalvas.size());
    }
}