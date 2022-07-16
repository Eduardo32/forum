package com.pauloeduardocosta.forum.repository;

import com.pauloeduardocosta.forum.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRespostaRepository extends JpaRepository<Resposta, Long> {
}
