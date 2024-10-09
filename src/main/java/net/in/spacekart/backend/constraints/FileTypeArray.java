package net.in.spacekart.backend.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.in.spacekart.backend.validators.FileTypeArrayValidator;
import net.in.spacekart.backend.validators.FileTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileTypeArrayValidator.class)
public @interface FileTypeArray {
    String fileType() default "image";

    String message() default "Invalid file type";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}