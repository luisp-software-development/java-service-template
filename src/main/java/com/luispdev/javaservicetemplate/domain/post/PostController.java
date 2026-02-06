package com.luispdev.javaservicetemplate.domain.post;

import com.luispdev.javaservicetemplate.domain.post.model.Post;
import com.luispdev.javaservicetemplate.domain.post.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.domain.post.dto.UpdatePostDTO;
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
    public Post create(@RequestBody CreatePostDTO post) {
        return service.create(post);
    }

    @GetMapping
    public List<Post> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody UpdatePostDTO post) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
