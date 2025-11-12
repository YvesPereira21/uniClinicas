package projetos.uniClinicas.dto.requests;

import projetos.uniClinicas.dto.EnderecoDTO;
import projetos.uniClinicas.dto.UsuarioDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalTime;

public class ClinicaRequestDTO {

    @NotBlank
    @CNPJ
    private String cpnjClinica;
    @NotBlank
    private String nomeClinica;
    @NotNull
    private int telefone;
    @NotNull
    private LocalTime horarioFuncionamento;
    @NotNull
    private LocalTime horarioFechamento;
    @NotNull
    @Valid
    private UsuarioDTO usuario;
    @NotNull
    @Valid
    private EnderecoDTO endereco;

    public ClinicaRequestDTO() {
    }

    public ClinicaRequestDTO(String cpnjClinica, String nomeClinica, int telefone, LocalTime horarioFuncionamento, LocalTime horarioFechamento, EnderecoDTO endereco) {
        this.cpnjClinica = cpnjClinica;
        this.nomeClinica = nomeClinica;
        this.telefone = telefone;
        this.horarioFuncionamento = horarioFuncionamento;
        this.horarioFechamento = horarioFechamento;
        this.endereco = endereco;
    }

    public String getCpnjClinica() {
        return cpnjClinica;
    }

    public void setCpnjClinica(String cpnj_clinica) {
        this.cpnjClinica = cpnj_clinica;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public LocalTime getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(LocalTime horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public EnderecoDTO getEndereco() {
        return this.endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}

