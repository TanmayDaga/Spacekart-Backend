package net.in.spacekart.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import net.in.spacekart.backend.constraints.FileTypeArray;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileTypeArrayValidator implements ConstraintValidator<FileTypeArray, MultipartFile[]> {

    String fileType;
    String message;
    boolean nullable;

    @Autowired
    Tika tika;

    @Override
    public void initialize(FileTypeArray constraintAnnotation ) {
        this.fileType = constraintAnnotation.fileType();
        this.message = constraintAnnotation.message();
        this.nullable = constraintAnnotation.nullable();
    }


    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
        if(value == null && nullable) {
            return true;
        }else if(value == null && !nullable) {
            return  false;
        }
        else {
            if(value.length == 0 && nullable) return  true;
            else if(value.length == 0 && !nullable) return  false;
            for (MultipartFile file : value) {

                try {
                    if(!tika.detect(file.getInputStream()).contains(fileType)){
                        return false;
                    }
                } catch (IOException ignored) {
                    return  false;
                }
            }
            return true;

        }


    }
}
