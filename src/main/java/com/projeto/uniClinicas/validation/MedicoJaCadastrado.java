package com.projeto.uniClinicas.validation;

public class MedicoJaCadastrado extends RuntimeException {
    public MedicoJaCadastrado(String message) {
        super(message);
    }
}
