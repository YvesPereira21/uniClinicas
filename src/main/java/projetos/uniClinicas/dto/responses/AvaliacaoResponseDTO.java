package projetos.uniClinicas.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AvaliacaoResponseDTO {

    private String comentario;
    private Integer nota;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime horarioComentario;
    private String nomeUsuario;

    public AvaliacaoResponseDTO() {
    }

    public AvaliacaoResponseDTO(String comentario, Integer nota, String nomeUsuario) {
        this.comentario = comentario;
        this.nota = nota;
        this.horarioComentario = LocalDateTime.now();
        this.nomeUsuario = nomeUsuario;
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

    public LocalDateTime getHorarioComentario() {
        return horarioComentario;
    }

    public void setHorarioComentario(LocalDateTime horarioComentario) {
        this.horarioComentario = horarioComentario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

}
