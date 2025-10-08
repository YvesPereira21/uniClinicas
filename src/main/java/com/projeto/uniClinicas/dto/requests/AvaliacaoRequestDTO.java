package com.projeto.uniClinicas.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoRequestDTO {

    private String comentario;
    @NotNull
    @Min(0) @Max(5)
    private Integer nota;
    public AvaliacaoRequestDTO() {
    }

    public AvaliacaoRequestDTO(String comentario, Integer nota) {
        this.comentario = comentario;
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

}
