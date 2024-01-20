package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorJWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(ErrorCreatingJWTException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorCreatingJWTException(
            ErrorCreatingJWTException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }

    @ExceptionHandler(ErrorJWTVerificationException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleErrorJWTVerificationException(
            ErrorJWTVerificationException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleBadCredentialsException(
            BadCredentialsException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bodyResponse);
    }
}
