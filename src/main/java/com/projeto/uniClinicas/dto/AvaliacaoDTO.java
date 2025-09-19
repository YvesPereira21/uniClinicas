package com.projeto.uniClinicas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AvaliacaoDTO {

    private String comentario;
    @NotNull
    @Min(0)
    @Max(5)
    private Integer nota;
    private LocalDateTime horarioComentario;

    public AvaliacaoDTO(String comentario, Integer nota) {
        this.comentario = comentario;
        this.nota = nota;
        this.horarioComentario = LocalDateTime.now();
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getNota() {
        return nota;
    }

    public LocalDateTime getHorarioComentario() {
        return horarioComentario;
    }
}
