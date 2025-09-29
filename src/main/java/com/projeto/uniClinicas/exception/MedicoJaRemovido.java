package com.projeto.uniClinicas.exception;

public class MedicoJaRemovido extends RuntimeException {
    public MedicoJaRemovido(String message) {
        super(message);
    }
}
