package com.projeto.uniClinicas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CidadeParaibaValidator.class)
@Documented
public @interface CidadeParaiba {

    String message() default "Essa não é uma cidade válida";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
