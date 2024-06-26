package net.in.spacekart.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.in.spacekart.backend.constraints.Rating;

public class RatingValidator implements ConstraintValidator<Rating, Float> {
    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        return value <= 5 && value >= 0 && (value % 0.5 == 0);
    }

    @Override
    public void initialize(Rating constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
