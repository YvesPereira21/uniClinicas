package projetos.uniClinicas.mapper;

import projetos.uniClinicas.dto.MedicoDTO;
import projetos.uniClinicas.model.Medico;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {
    private final ModelMapper mapper;

    public MedicoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MedicoDTO convertToDTO(Medico medico){
        return this.mapper.map(medico,MedicoDTO.class);
    }

    public Medico convertToEntity(MedicoDTO medicoDTO){
        return this.mapper.map(medicoDTO,Medico.class);
    }
}
