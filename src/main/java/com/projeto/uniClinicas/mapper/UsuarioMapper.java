package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.UsuarioRequestDTO;
import com.projeto.uniClinicas.dto.UsuarioResponseDTO;
import com.projeto.uniClinicas.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioResponseDTO convertToDTO(Usuario usuario){
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }
    public Usuario convertToEntity(UsuarioRequestDTO usuarioRequestDTO){
        return modelMapper.map(usuarioRequestDTO, Usuario.class);
    }
}

