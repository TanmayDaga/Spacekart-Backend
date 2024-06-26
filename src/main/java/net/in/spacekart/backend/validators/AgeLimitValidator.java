package net.in.spacekart.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.in.spacekart.backend.constraints.AgeLimit;


import java.time.OffsetDateTime;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, OffsetDateTime> {
    int minimumAge;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.minimumAge = constraintAnnotation.minimumAge();
    }



    @Override
    public boolean isValid(OffsetDateTime birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if(birthDate == null){
            return false;
        }
        OffsetDateTime today = OffsetDateTime.now();
       OffsetDateTime minimumAgeYearsAgo = today.minusYears(this.minimumAge);
        return !minimumAgeYearsAgo.isBefore(birthDate);
    }
}