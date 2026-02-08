package com.luispdev.javaservicetemplate.domain;

import com.luispdev.javaservicetemplate.application.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.application.dto.UpdatePostDTO;
import com.luispdev.javaservicetemplate.infrastructure.PostEntity;
import com.luispdev.javaservicetemplate.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public PostEntity create(@RequestBody CreatePostDTO postDTO) {
        var post = new PostEntity();
        post.setTitle(postDTO.title());
        post.setContent(postDTO.content());
        return repository.save(post);
    }

    public List<PostEntity> findAll() {
        return repository.findAll();
    }

    public PostEntity findById(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public PostEntity update(@PathVariable Long id, @RequestBody UpdatePostDTO updated) {
        PostEntity postEntity = findById(id);
        postEntity.setTitle(updated.title());
        postEntity.setContent(updated.content());
        return repository.save(postEntity);
    }

    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
