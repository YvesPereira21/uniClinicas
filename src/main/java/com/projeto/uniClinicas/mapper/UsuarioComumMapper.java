package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.UsuarioComumRequestDTO;
import com.projeto.uniClinicas.dto.UsuarioComumResponseDTO;
import com.projeto.uniClinicas.model.Endereco;
import com.projeto.uniClinicas.model.Municipio;
import com.projeto.uniClinicas.model.UsuarioComum;
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