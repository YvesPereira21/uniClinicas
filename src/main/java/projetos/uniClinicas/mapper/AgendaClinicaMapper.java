package projetos.uniClinicas.mapper;

import projetos.uniClinicas.dto.AgendaClinicaDTO;
import projetos.uniClinicas.model.AgendaClinica;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AgendaClinicaMapper {

    private final ModelMapper mapper;

    public AgendaClinicaMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public AgendaClinicaDTO convertToDTO(AgendaClinica agendaClinica){
        return this.mapper.map(agendaClinica, AgendaClinicaDTO.class);
    }

    public AgendaClinica convertToEntity(AgendaClinicaDTO agendaClinicaDTO){
        return this.mapper.map(agendaClinicaDTO, AgendaClinica.class);
    }
}
