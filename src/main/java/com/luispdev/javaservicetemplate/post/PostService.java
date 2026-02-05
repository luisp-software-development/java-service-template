package com.luispdev.javaservicetemplate.post;

import com.luispdev.javaservicetemplate.post.domain.Post;
import com.luispdev.javaservicetemplate.post.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.post.dto.UpdatePostDTO;
import com.luispdev.javaservicetemplate.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public Post create(@RequestBody CreatePostDTO postDTO) {
        var post = new Post();
        post.setTitle(postDTO.title());
        post.setContent(postDTO.content());
        return repository.save(post);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public Post update(@PathVariable Long id, @RequestBody UpdatePostDTO updated) {
        Post post = findById(id);
        post.setTitle(updated.title());
        post.setContent(updated.content());
        return repository.save(post);
    }

    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
