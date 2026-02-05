package com.luispdev.javaservicetemplate.post;

import com.luispdev.javaservicetemplate.post.exception.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> notFound() {
        return ResponseEntity.notFound().build();
    }

}
