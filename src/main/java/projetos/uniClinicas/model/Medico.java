package projetos.uniClinicas.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tb_medico")
@ToString
@EqualsAndHashCode
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medico_id")
    private Long medicoId;
    @Column(name = "crm_medico", unique = true, nullable = false)
    private String crmMedico;
    @Column(name = "nome_medico")
    private String nomeMedico;
    @Column(name = "especialidade")
    private String especialidade;
    @OneToMany(mappedBy = "medico", orphanRemoval = true)
    private List<AgendaClinica> agendaClinicas;

    public Medico() {}

    public Long getMedicoId() {
        return medicoId;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}

