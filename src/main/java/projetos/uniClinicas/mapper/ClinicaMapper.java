package projetos.uniClinicas.mapper;

import projetos.uniClinicas.dto.requests.ClinicaRequestDTO;
import projetos.uniClinicas.dto.responses.ClinicaResponseDTO;
import projetos.uniClinicas.model.Clinica;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClinicaMapper {
    private final ModelMapper modelMapper;

    public ClinicaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClinicaResponseDTO convertToDTO(Clinica clinica) {
        return modelMapper.map(clinica, ClinicaResponseDTO.class);
    }

    public Clinica convertToEntity(ClinicaRequestDTO dto) {
        return modelMapper.map(dto, Clinica.class);
    }
}