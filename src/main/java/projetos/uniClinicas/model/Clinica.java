package projetos.uniClinicas.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tb_clinica")
@ToString
@EqualsAndHashCode
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinica_id")
    private Long clinicaId;
    @Column(name = "cnpj_clinica", unique = true)
    private String cpnjClinica;
    @Column(name = "nome_clinica")
    private String nomeClinica;
    @Column(name = "telefone", length = 12)
    private int telefone;
    @Column(name = "horario_funcionamento")
    private LocalTime horarioFuncionamento;
    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoesDaClinica;
    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendaClinica> agendaClinicas;


    public Clinica(){}


    public Long getClinicaId() {
        return this.clinicaId;
    }

    public String getCpnjClinica() {
        return cpnjClinica;
    }

    public void setCpnjClinica(String cpnjClinica) {
        this.cpnjClinica = cpnjClinica;
    }

    public String getNomeClinica() {
        return this.nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public LocalTime getHorarioFuncionamento() {
        return this.horarioFuncionamento;
    }

    public void setHorarioFuncionamento(LocalTime horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public LocalTime getHorarioFechamento() {
        return this.horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

