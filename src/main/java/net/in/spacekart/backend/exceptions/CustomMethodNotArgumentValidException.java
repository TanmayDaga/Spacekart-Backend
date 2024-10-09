package net.in.spacekart.backend.exceptions;

public class CustomMethodNotArgumentValidException extends RuntimeException {


    public CustomMethodNotArgumentValidException(String value, String message) {

        super(value + message);

    }

    public CustomMethodNotArgumentValidException(String value) {
        super(value + "is not valid");
    }
}


