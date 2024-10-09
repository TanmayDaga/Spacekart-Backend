package net.in.spacekart.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.in.spacekart.backend.constraints.FileType;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    String fileType;
    String message;
    boolean nullable;

    @Autowired
    Tika tika;

    @Override
    public void initialize(FileType constraintAnnotation ) {
        this.fileType = constraintAnnotation.fileType();
        this.message = constraintAnnotation.message();
        this.nullable = constraintAnnotation.nullable();
    }


    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
       if(value == null && nullable) {
           return true;
       }else if(value != null) {
           try {
               if(tika.detect(value.getInputStream()).contains(fileType)){
                   return true;
               }
           } catch (IOException ignored) {

           }
       }

        return false;

    }
}
