package com.luispdev.javaservicetemplate.post;

import com.luispdev.javaservicetemplate.post.domain.Post;
import com.luispdev.javaservicetemplate.post.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.post.dto.UpdatePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping
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
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
