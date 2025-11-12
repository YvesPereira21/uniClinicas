package projetos.uniClinicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_avaliacao")
@ToString
@EqualsAndHashCode
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id")
    private Long avaliacaoId;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "nota")
    private double nota;
    @Column(name = "horario_comentario")
    private LocalDateTime horarioComentario;
    @ManyToOne
    @JoinColumn(name = "usuariocomum_id")
    private UsuarioComum usuarioComum;
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;

    public Avaliacao() {
        this.horarioComentario = LocalDateTime.now();
    }

    public Long getAvaliacaoId() {
        return avaliacaoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public LocalDateTime getHorarioComentario() {
        return horarioComentario;
    }

    public void setHorarioComentario(LocalDateTime horarioComentario) {
        this.horarioComentario = horarioComentario;
    }

    public UsuarioComum getUsuarioComum() {
        return usuarioComum;
    }

    public void setUsuarioComum(UsuarioComum usuarioComum) {
        this.usuarioComum = usuarioComum;
    }

    public Clinica getClinica() {
        return this.clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

}

