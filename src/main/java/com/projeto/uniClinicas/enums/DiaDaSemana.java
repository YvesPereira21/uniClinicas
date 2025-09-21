package com.projeto.uniClinicas.enums;

public enum DiaDaSemana {

    SEGUNDA ("Segunda"),
    TERCA ("Terça"),
    QUARTA ("Quarta"),
    QUINTA ("Quinta"),
    SEXTA ("Sexta"),
    SABADO ("Sábado"),
    DOMINGO ("Domingo");


    private final String diaDaSemana;

    DiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public static boolean diaDaSemanaValido(String diaDaSemana) {
        for(DiaDaSemana dia : DiaDaSemana.values()){
            if(dia.getDiaDaSemana().equals(diaDaSemana)){
                return true;
            }
        }
        return false;
    }
}
