package com.projeto.uniClinicas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EstadoValidator implements ConstraintValidator<Estado, String> {

    private final List<String> estado = List.of("Paraíba");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return estado.contains(s);
    }
}
