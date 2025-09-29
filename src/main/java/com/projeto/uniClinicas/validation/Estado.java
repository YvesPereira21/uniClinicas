package com.projeto.uniClinicas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EstadoValidator.class)
@Documented
public @interface Estado {

    String message() default "Esse não é um estado válido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
