package com.ocsoares.advancedcrudspringboot.domain.exceptions.response;

public record InvalidRequestBodyException(String field, String message) {
}
