package net.in.spacekart.backend.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.in.spacekart.backend.validators.AgeLimitValidator;
import net.in.spacekart.backend.validators.RatingValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface Rating {
    String message() default "Rating must be 0.5 or Integer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
