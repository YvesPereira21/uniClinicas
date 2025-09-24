package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.ClinicaDTO;
import com.projeto.uniClinicas.model.Clinica;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClinicaMapper {

    private final ModelMapper mapper;

    public ClinicaMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ClinicaDTO convertToDTO(Clinica clinica){
        return this.mapper.map(clinica,ClinicaDTO.class);
    }

    public Clinica convertToEntity(ClinicaDTO clinicaDTO){
        return this.mapper.map(clinicaDTO,Clinica.class);
    }
}
