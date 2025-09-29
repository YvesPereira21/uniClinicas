package com.projeto.uniClinicas.validation;

public class MedicoJaRemovido extends RuntimeException {
    public MedicoJaRemovido(String message) {
        super(message);
    }
}
