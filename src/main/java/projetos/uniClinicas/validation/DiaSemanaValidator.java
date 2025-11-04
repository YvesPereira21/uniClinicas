package com.projeto.uniClinicas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class DiaSemanaValidator implements ConstraintValidator<DiaSemana, String> {

    private final List<String> diaDaSemanaValido= List.of(
            "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"
    );

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return diaDaSemanaValido.contains(value);
    }
}
