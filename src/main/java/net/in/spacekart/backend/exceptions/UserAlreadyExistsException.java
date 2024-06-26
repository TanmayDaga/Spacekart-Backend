package net.in.spacekart.backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends RuntimeException {

    private String message;
    public UserAlreadyExistsException(String message) {
        this.message = message;
    }

}
