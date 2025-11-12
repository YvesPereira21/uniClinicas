package projetos.uniClinicas.dto.auxiliares;

import projetos.uniClinicas.dto.MedicoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AtualizaAgendaClinicaDTO {

    @NotBlank
    private String crmMedicoAntigo;

    @NotNull
    @Valid
    private MedicoDTO medicoContratado;

    @NotEmpty
    @Valid
    private List<HorarioDTO> horarios;

    public AtualizaAgendaClinicaDTO() {
    }

    public String getCrmMedicoAntigo() {
        return crmMedicoAntigo;
    }

    public void setCrmMedicoAntigo(String crmMedicoAntigo) {
        this.crmMedicoAntigo = crmMedicoAntigo;
    }

    public MedicoDTO getMedicoContratado() {
        return medicoContratado;
    }

    public void setMedicoContratado(MedicoDTO medicoContratado) {
        this.medicoContratado = medicoContratado;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}