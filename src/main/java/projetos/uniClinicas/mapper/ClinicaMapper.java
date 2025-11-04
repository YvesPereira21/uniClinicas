package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.requests.ClinicaRequestDTO;
import com.projeto.uniClinicas.dto.responses.ClinicaResponseDTO;
import com.projeto.uniClinicas.model.Clinica;
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