package edu.cad.study.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "edu.cad.study.web")
public class ActionExceptionHandler {
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity handleUnsupportedOperation(Exception e) {
        return ResponseEntity.badRequest()
                .body("{\"message\": \"Unsupported operation\"}");
    }
}
