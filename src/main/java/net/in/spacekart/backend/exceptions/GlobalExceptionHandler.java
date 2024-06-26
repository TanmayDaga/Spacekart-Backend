package net.in.spacekart.backend.exceptions;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.security.auth.login.CredentialException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidArgumentsInRequestBody(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            resp.put(((FieldError) error).getField(), error.getDefaultMessage());
            System.out.println(error.getObjectName());

        });

        return  new ResponseEntity<Map<String , String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> invalidUser(AuthenticationException ex){
        return new ResponseEntity<String>("Invalid User",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class, DataIntegrityViolationException.class})
    public ResponseEntity<String> conflict(DataIntegrityViolationException e) {

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        return new ResponseEntity<String>((message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CredentialException.class})
    public ResponseEntity<String> invalidPassword(CredentialException ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }



}
