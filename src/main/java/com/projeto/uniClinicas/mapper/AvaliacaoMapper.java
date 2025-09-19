package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.AvaliacaoDTO;
import com.projeto.uniClinicas.model.Avaliacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    private final ModelMapper modelMapper;

    public AvaliacaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AvaliacaoDTO convertToDTO(Avaliacao avaliacao) {
        return modelMapper.map(avaliacao, AvaliacaoDTO.class);
    }

    public Avaliacao convertToEntity(AvaliacaoDTO dto) {
        return modelMapper.map(dto, Avaliacao.class);
    }
}