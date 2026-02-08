package com.luispdev.javaservicetemplate.application;

import com.luispdev.javaservicetemplate.application.dto.ErrorResponseDTO;
import com.luispdev.javaservicetemplate.domain.PostNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class PostExceptionHandler {

    private final MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponseDTO notFound() {
        return buildResponseDTO(ErrorMessages.POST_NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponseDTO handleValidationExceptions(ConstraintViolationException ex) {
        var error = ex.getConstraintViolations().stream().findFirst().get();
        return buildResponseDTO(error.getMessageTemplate());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseDTO handleUnmappedExceptions(RuntimeException ex) {
        return buildResponseDTO(ErrorMessages.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDTO buildResponseDTO(String code) {
        var message = messageSource.getMessage(code, new Object[]{}, LocaleContextHolder.getLocale());
        return new ErrorResponseDTO(code, message);
    }

}
