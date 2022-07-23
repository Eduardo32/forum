package com.pauloeduardocosta.forum.repository;

import com.pauloeduardocosta.forum.model.VerificacaoEmail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVerificacaoEmailRepository extends MongoRepository<VerificacaoEmail, String> {
    Optional<VerificacaoEmail> findByUuid(String uuid);
}
