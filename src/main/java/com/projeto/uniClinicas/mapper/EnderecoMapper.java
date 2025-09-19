package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.EnderecoDTO;
import com.projeto.uniClinicas.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    private final ModelMapper mapper;

    public EnderecoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EnderecoDTO convertToDTO(Endereco endereco){
        return this.mapper.map(endereco,EnderecoDTO.class);
    }

    public Endereco convertToEntity(EnderecoDTO enderecoDTO){
        return this.mapper.map(enderecoDTO,Endereco.class);
    }
}
