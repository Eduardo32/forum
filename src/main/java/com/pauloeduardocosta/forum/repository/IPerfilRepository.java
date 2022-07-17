package com.pauloeduardocosta.forum.repository;

import com.pauloeduardocosta.forum.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
}
