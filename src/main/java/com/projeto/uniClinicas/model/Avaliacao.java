package com.projeto.uniClinicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id")
    private Long avaliacaoId;
    @NotBlank
    @Column(name = "comentario")
    private String comentario;
    @Min(0)
    @Max(5)
    @Column(name = "nota")
    private double nota;
    @Column(name = "horario_comentario")
    private LocalDateTime horarioComentario;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Clinica getClinica() {
        return this.clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

}

