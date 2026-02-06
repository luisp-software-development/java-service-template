package com.luispdev.javaservicetemplate.domain;

public record ErrorResponse(
        String code,
        String message
) {
}
