package com.luispdev.javaservicetemplate.application;

import com.luispdev.javaservicetemplate.application.dto.ErrorResponseDTO;
import com.luispdev.javaservicetemplate.domain.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PostExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponseDTO notFound() {
        var code = "error.post.notfound";
        var message = "Post not found.";
        return new ErrorResponseDTO(code, message);
    }

}
