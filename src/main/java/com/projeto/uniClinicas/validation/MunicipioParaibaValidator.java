package com.projeto.uniClinicas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MunicipioParaibaValidator implements ConstraintValidator<MunicipiosParaiba, String > {

    private final List<String> municipiosValidos = List.of(
            "Mamanguape", "Piancó", "Sapé", "Jacaraú", "Cabedelo",
            "Rio Tinto", "Bananeiras", "Patos", "Alagoa Grande",
            "Mato Grosso", "São Bento"
    );

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return municipiosValidos.contains(s);
    }
}
