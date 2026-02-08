package com.luispdev.javaservicetemplate.application;

import com.luispdev.javaservicetemplate.domain.PostService;
import com.luispdev.javaservicetemplate.infrastructure.PostEntity;
import com.luispdev.javaservicetemplate.application.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.application.dto.UpdatePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostEntity create(@RequestBody CreatePostDTO post) {
        return service.create(post);
    }

    @GetMapping
    public List<PostEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PostEntity findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PostEntity update(@PathVariable Long id, @RequestBody UpdatePostDTO post) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
