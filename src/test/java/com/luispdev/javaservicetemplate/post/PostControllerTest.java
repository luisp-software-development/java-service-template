package com.luispdev.javaservicetemplate.post;

import com.luispdev.javaservicetemplate.post.domain.Post;
import com.luispdev.javaservicetemplate.post.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.post.dto.UpdatePostDTO;
import com.luispdev.javaservicetemplate.post.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePost() throws Exception {
        CreatePostDTO dto = new CreatePostDTO("Title", "Content");
        Post saved = new Post(1L, "Title", "Content");

        when(service.create(any())).thenReturn(saved);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));
    }

    @Test
    void shouldReturnAllPosts() throws Exception {
        List<Post> posts = List.of(
                new Post(1L, "A", "B"),
                new Post(2L, "C", "D")
        );

        when(service.findAll()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("A"));
    }

    @Test
    void shouldReturnPostById() throws Exception {
        Post post = new Post(1L, "Title", "Content");

        when(service.findById(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenPostNotFound() throws Exception {
        when(service.findById(99L)).thenThrow(new PostNotFoundException());

        mockMvc.perform(get("/posts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePost() throws Exception {
        UpdatePostDTO dto = new UpdatePostDTO("New", "Updated");
        Post updated = new Post(1L, "New", "Updated");

        when(service.update(any(), any())).thenReturn(updated);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"));
    }

    @Test
    void shouldDeletePost() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isOk());
    }
}