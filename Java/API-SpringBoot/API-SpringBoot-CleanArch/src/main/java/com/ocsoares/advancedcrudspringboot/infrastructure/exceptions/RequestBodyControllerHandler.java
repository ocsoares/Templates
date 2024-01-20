package com.ocsoares.advancedcrudspringboot.infrastructure.exceptions;

import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.InvalidRequestBodyException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.response.MessageAndStatusCodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RequestBodyControllerHandler {
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<MessageAndStatusCodeResponse> handleInvalidRequestBody(
    ) {
        MessageAndStatusCodeResponse bodyResponse = new MessageAndStatusCodeResponse(
                "The request body must be in valid JSON format", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidRequestBodyException>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {

        List<FieldError> fieldErrorsList = exception.getBindingResult().getFieldErrors();

        List<InvalidRequestBodyException> invalidRequestBodyExceptionList = new ArrayList<>();

        fieldErrorsList.forEach(error -> invalidRequestBodyExceptionList.add(
                new InvalidRequestBodyException(error.getField(), error.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidRequestBodyExceptionList);
    }
}
