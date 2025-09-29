package com.projeto.uniClinicas.validation;

import com.projeto.uniClinicas.enums.CidadesParaiba;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CidadeParaibaValidator implements ConstraintValidator<CidadeParaiba, String > {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return CidadesParaiba.cidadeValida(s);
    }
}
