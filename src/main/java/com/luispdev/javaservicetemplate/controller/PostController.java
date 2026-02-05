package com.luispdev.javaservicetemplate.controller;

import com.luispdev.javaservicetemplate.model.Post;
import com.luispdev.javaservicetemplate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository repository;

    @GetMapping
    public List<Post> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return repository.save(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post updated) {
        Post post = repository.findById(id).orElseThrow();
        post.setTitle(updated.getTitle());
        post.setContent(updated.getContent());
        return repository.save(post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
