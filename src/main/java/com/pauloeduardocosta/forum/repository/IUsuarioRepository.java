package com.pauloeduardocosta.forum.repository;

import com.pauloeduardocosta.forum.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String username);

    Optional<Usuario> findByVerificacaoEmailUuid(String uuid);
}
