package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.MunicipioDTO;
import com.projeto.uniClinicas.model.Municipio;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MunicipioMapper {

    private final ModelMapper mapper;

    public MunicipioMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public MunicipioDTO convertToDTO(Municipio municipio){
        return this.mapper.map(municipio, MunicipioDTO.class);
    }

    public Municipio convertToEntity(MunicipioDTO municipioDTO){
        return this.mapper.map(municipioDTO,Municipio.class);
    }

}
