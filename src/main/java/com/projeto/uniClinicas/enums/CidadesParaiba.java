package com.projeto.uniClinicas.enums;

public enum CidadesParaiba {
    MAMANGUAPE("Mamanguape"),
    PIANCO("Piancó"),
    SAPE("Sapé"),
    JACARAU("Jacaraú"),
    CABEDELO("Cabedelo"),
    RIO_TINTO("Rio Tinto"),
    BANANEIRAS("Bananeiras"),
    PATOS("Patos"),
    ALAGOA_GRANDE("Alagoa Grande"),
    MATO_GROSSO("Mato Grosso"),
    SAO_BENTO("São Bento");

    private final String nome;

    CidadesParaiba(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static boolean cidadeValida(String diaDaSemana) {
        for(CidadesParaiba dia : CidadesParaiba.values()){
            if(dia.getNome().equals(diaDaSemana)){
                return true;
            }
        }
        return false;
    }
}
