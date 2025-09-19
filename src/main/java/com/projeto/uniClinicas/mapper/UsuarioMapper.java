package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.UsuarioDTO;
import com.projeto.uniClinicas.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioDTO convertToDTO(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    public Usuario convertToEntity(UsuarioDTO usuarioDTO){
        return modelMapper.map(usuarioDTO, Usuario.class);
    }
}

