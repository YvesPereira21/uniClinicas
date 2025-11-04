package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
