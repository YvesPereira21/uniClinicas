package projetos.uniClinicas.repository;

import projetos.uniClinicas.model.Clinica;
import projetos.uniClinicas.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
