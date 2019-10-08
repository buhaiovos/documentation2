package edu.cad.controllers.exceptions;

public class ResponseProcessingException extends RuntimeException {
    public ResponseProcessingException(String message) {
        super(message);
    }
}
