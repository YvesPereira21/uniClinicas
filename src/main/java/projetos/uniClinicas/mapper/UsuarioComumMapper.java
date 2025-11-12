package projetos.uniClinicas.mapper;

import projetos.uniClinicas.dto.requests.UsuarioComumRequestDTO;
import projetos.uniClinicas.dto.responses.UsuarioComumResponseDTO;
import projetos.uniClinicas.model.UsuarioComum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioComumMapper {

    private final ModelMapper modelMapper;

    public UsuarioComumMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioComumResponseDTO convertToDTO(UsuarioComum usuarioComum){
        return modelMapper.map(usuarioComum, UsuarioComumResponseDTO.class);
    }
    public UsuarioComum convertToEntity(UsuarioComumRequestDTO usuarioComumRequestDTO){
        return modelMapper.map(usuarioComumRequestDTO, UsuarioComum.class);
    }
}