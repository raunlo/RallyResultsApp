package com.ralohmus.rallyresults.rest.util;

import com.ralohmus.rallyresults.persistence.exception.ResourceAlreadyAssociatedException;
import com.ralohmus.rallyresults.persistence.exception.ResourceConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAlreadyAssociatedException.class)
    public ResponseEntity<Error> handleError(ResourceAlreadyAssociatedException ex, WebRequest req) {
        var error = new Error().setError("Cannot delete or update entity, entity already associated with another entity")
                .setCode(422);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Error> handleError(ResourceConflictException ex, WebRequest req) {
        var error = new Error().setError(ex.getMessage())
                .setCode(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
