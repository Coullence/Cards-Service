package com.google.cms.utilities;

import com.google.cms.utilities.exception.CustomExceptions;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomExceptions.ColorFormatException.class)
    public ResponseEntity<?> handleColorFormatException(CustomExceptions.ColorFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Color should conform to the format '#RRGGBB'");
    }
    @ExceptionHandler(CustomExceptions.JwtTokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleJwtTokenExpiredException(CustomExceptions.JwtTokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Require authentication: " + e.getMessage());
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<String> handleUnauthorizedException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: " + e.getMessage());
    }
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: " + e.getMessage());
    }

    @ExceptionHandler(CustomExceptions.NotAcceptableException.class)
    public ResponseEntity<String> handleResourceAlreadyExistsException(CustomExceptions.NotAcceptableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: " + e.getMessage());
    }

    @ExceptionHandler(CustomExceptions.ResourceDeletedException.class)
    public ResponseEntity<String> handleResourceDeletedException(CustomExceptions.ResourceDeletedException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler(CustomExceptions.SuccessfulRequestNoContentException.class)
    public ResponseEntity<String> handleSuccessfulRequestNoContentException(CustomExceptions.SuccessfulRequestNoContentException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Content: " + e.getMessage());
    }
}

