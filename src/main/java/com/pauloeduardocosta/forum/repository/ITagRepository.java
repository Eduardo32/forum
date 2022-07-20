package com.pauloeduardocosta.forum.repository;

import com.pauloeduardocosta.forum.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNomeIgnoreCase(String tag);
}
