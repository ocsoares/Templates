package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsByEmailException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleUserAlreadyExistsByEmailException(
            UserAlreadyExistsByEmailException exception
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleMethodArgumentTypeMismatchException() {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(
                new InvalidUserByIdException().getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyResponse);
    }

    @ExceptionHandler(InvalidUserByIdException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleInvalidUserByIdException(
            InvalidUserByIdException exception
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyResponse);
    }

    @ExceptionHandler(InvalidUserByEmailException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleInvalidUserByEmailException(
            InvalidUserByEmailException exception
    ) {
        var bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyResponse);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleUnsupportedOperationException(
            UnsupportedOperationException exception
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(exception.getMessage(),
                HttpStatus.NOT_IMPLEMENTED.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(bodyResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageAndStatusCodeResponse> handleGeneralException() {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(
                "An unexpected server error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyResponse);
    }
}
