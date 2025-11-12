package projetos.uniClinicas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DiaSemanaValidator.class)
@Documented
public @interface DiaSemana {

    String message() default "Esse não é um dia da semana válido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
