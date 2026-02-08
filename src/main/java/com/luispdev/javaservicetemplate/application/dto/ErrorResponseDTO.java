package com.luispdev.javaservicetemplate.application.dto;

public record ErrorResponseDTO(
        String code,
        String message
) {
}
