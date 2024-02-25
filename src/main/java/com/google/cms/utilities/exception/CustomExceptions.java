package com.google.cms.utilities.exception;

import org.springframework.stereotype.Service;

@Service
public class CustomExceptions {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
    public static class ColorFormatException extends IllegalArgumentException{
        public ColorFormatException(String message) {
            super(message);
        }
    }

    public static class NotAcceptableException extends RuntimeException {
        public NotAcceptableException(String message) {
            super(message);
        }
    }
    public class JwtTokenExpiredException extends RuntimeException {
        public JwtTokenExpiredException(String message) {
            super(message);
        }
    }
    public static class ResourceDeletedException extends RuntimeException {
        public ResourceDeletedException(String message) {
            super(message);
        }
    }
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }


    public static class SuccessfulRequestNoContentException extends RuntimeException {
        public SuccessfulRequestNoContentException(String message) {
            super(message);
        }
    }

}
