package com.projeto.uniClinicas.validation;

import com.projeto.uniClinicas.enums.DiaDaSemana;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DiaSemanaValidator implements ConstraintValidator<DiaSemana, String> {

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return DiaDaSemana.diaDaSemanaValido(value);
    }
}
