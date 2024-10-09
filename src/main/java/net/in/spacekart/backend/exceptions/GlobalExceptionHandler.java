package net.in.spacekart.backend.exceptions;

import jakarta.persistence.NoResultException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.security.auth.login.CredentialException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidArgumentsInRequestBody(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            resp.put(((FieldError) error).getField(),ex.getBody().getDetail());
            System.out.println(error.getObjectName());

        });

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomMethodNotArgumentValidException.class)
    public ResponseEntity<String> invalidArgumentsInRequestBody(CustomMethodNotArgumentValidException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> invalidUser(AuthenticationException ex) {
        return new ResponseEntity<String>("Invalid User", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class, DataIntegrityViolationException.class})
    public ResponseEntity<String> conflict(DataIntegrityViolationException e) {

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        return new ResponseEntity<String>((message), HttpStatus.CONFLICT);
    }


    @ExceptionHandler({CredentialException.class})
    public ResponseEntity<String> invalidPassword(CredentialException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        e.printStackTrace();
    }


    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handle(NoResultException e) {
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpacekartBaseException.class)
    public ResponseEntity<?> handleSpaceKartBaseException(SpacekartBaseException e){
        return new ResponseEntity<>(e.getResponseMessage(), e.getStatusCode());
    }

}
