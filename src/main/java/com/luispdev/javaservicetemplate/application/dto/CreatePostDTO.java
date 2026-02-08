package com.luispdev.javaservicetemplate.application.dto;

import com.luispdev.javaservicetemplate.application.ErrorMessages;
import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(
        @NotBlank(message = ErrorMessages.POST_TITLE_REQUIRED)
        String title,
        String content
) {
}
