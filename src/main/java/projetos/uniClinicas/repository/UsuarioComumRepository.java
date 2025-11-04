package com.projeto.uniClinicas.repository;

import com.projeto.uniClinicas.model.Usuario;
import com.projeto.uniClinicas.model.UsuarioComum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioComumRepository extends JpaRepository<UsuarioComum,Long> {
    boolean existsByCpf(String cpf);
    UsuarioComum findByUsuarioUsername(String username);
    UsuarioComum findByUsuario(Usuario usuario);
}
