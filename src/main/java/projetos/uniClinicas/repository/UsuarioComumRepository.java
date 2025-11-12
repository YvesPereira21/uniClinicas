package projetos.uniClinicas.repository;

import projetos.uniClinicas.model.Usuario;
import projetos.uniClinicas.model.UsuarioComum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioComumRepository extends JpaRepository<UsuarioComum,Long> {
    boolean existsByCpf(String cpf);
    UsuarioComum findByUsuarioUsername(String username);
    UsuarioComum findByUsuario(Usuario usuario);
}
