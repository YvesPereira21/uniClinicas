package projetos.uniClinicas.dto;

import jakarta.validation.constraints.NotBlank;

public class MedicoDTO {

    @NotBlank
    private String nomeMedico;
    @NotBlank
    private String crmMedico;
    @NotBlank
    private String especialidade;

    public MedicoDTO() {
    }

    public MedicoDTO(String nomeMedico, String crmMedico, String especialidade) {
        this.nomeMedico = nomeMedico;
        this.crmMedico = crmMedico;
        this.especialidade = especialidade;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
