package com.projeto.uniClinicas.mapper;

import com.projeto.uniClinicas.dto.requests.AvaliacaoRequestDTO;
import com.projeto.uniClinicas.dto.responses.AvaliacaoResponseDTO;
import com.projeto.uniClinicas.model.Avaliacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    private final ModelMapper modelMapper;

    public AvaliacaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AvaliacaoResponseDTO convertToDTO(Avaliacao avaliacao) {
        AvaliacaoResponseDTO dto = modelMapper.map(avaliacao, AvaliacaoResponseDTO.class);

        if (avaliacao.getUsuarioComum() != null) {
            dto.setNomeUsuario(avaliacao.getUsuarioComum().getNome());
        }

        return dto;
    }
    public Avaliacao convertToEntity(AvaliacaoRequestDTO dto) {
        return modelMapper.map(dto, Avaliacao.class);
    }
}