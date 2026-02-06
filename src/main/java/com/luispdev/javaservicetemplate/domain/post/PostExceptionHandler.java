package com.luispdev.javaservicetemplate.domain.post;

import com.luispdev.javaservicetemplate.domain.ErrorResponse;
import com.luispdev.javaservicetemplate.domain.post.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PostExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorResponse notFound() {
        var code = "error.post.notfound";
        var message = "Post not found.";
        return new ErrorResponse(code, message);
    }

}
