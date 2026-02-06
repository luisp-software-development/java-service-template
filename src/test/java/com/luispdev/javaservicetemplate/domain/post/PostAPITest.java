package com.luispdev.javaservicetemplate.domain.post;

import com.luispdev.javaservicetemplate.domain.AbstractIntegrationTest;
import com.luispdev.javaservicetemplate.domain.post.model.Post;
import com.luispdev.javaservicetemplate.domain.post.dto.CreatePostDTO;
import com.luispdev.javaservicetemplate.domain.post.dto.UpdatePostDTO;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DisplayName("/posts")
class PostAPITest extends AbstractIntegrationTest {

    @Autowired
    private PostRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Create: should return post")
    public void givenValidRequestBody_whenPostingPost_shouldReturnCreatedPost() throws Exception {
        // given
        var validNewPost = Instancio.create(CreatePostDTO.class);

        // when
        var response = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validNewPost)));

        // then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(validNewPost.title()))
                .andExpect(jsonPath("$.content").value(validNewPost.content()));

    }

    @Test
    @DisplayName("Create: should persist post")
    public void givenValidNewPostBody_whenPostingPost_shouldPersistIt() throws Exception {
        // given
        var validNewPost = Instancio.create(CreatePostDTO.class);

        // when
        var responseBody = mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validNewPost)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdPostId = objectMapper.readValue(responseBody, Post.class).getId();

        // then
        var findByIdReturn = repository.findById(createdPostId);
        assertTrue(findByIdReturn.isPresent());

        var persistentPost = findByIdReturn.get();
        assertThat(persistentPost.getId()).isEqualTo(createdPostId);
        assertThat(persistentPost.getTitle()).isEqualTo(validNewPost.title());
        assertThat(persistentPost.getContent()).isEqualTo(validNewPost.content());
    }

    @Test
    @DisplayName("Find All: should return all data")
    void givenDatabaseWithExistingPosts_whenGettingPosts_shouldReturnAll() throws Exception {
        // given
        var existingPosts = setupExistingPosts();

        // when
        var response = mockMvc.perform(get("/posts"));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(existingPosts.size()))
                .andExpect(jsonPath("$", equalTo(existingPosts)));
    }

    @Test
    @DisplayName("Find All: should return empty array when there's no data")
    void givenEmptyDatabase_whenReceivingGetRequest_shouldReturnEmptyArray() throws Exception {
        // given
        // no setup - empty database

        // when
        var response = mockMvc.perform(get("/posts"));

        //then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @DisplayName("Find By Id: should return matching post")
    void givenExistingPostId_whenGettingPostById_shouldReturnMatchingPost() throws Exception {
        // given
        var existingPost = setupExistingPost();

        // when
        var response = mockMvc.perform(get("/posts/" + existingPost.getId()));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPost.getId()))
                .andExpect(jsonPath("$.title").value(existingPost.getTitle()))
                .andExpect(jsonPath("$.content").value(existingPost.getContent()));
    }

    @Test
    @DisplayName("Find By Id: should return not found error when given unknown id")
    void givenUnknownPostId_whenGettingPostById_shouldReturnNotFoundError() throws Exception {
        // given
        var unknownPostId = 999L;

        // when
        var response = mockMvc.perform(get("/posts/" + unknownPostId));

        // then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("error.post.notfound"))
                .andExpect(jsonPath("$.message").value("Post not found."));
    }

    @Test
    @DisplayName("Update By Id: should return updated post")
    void givenExistingPostIdAndValidPostChanges_whenUpdatingById_shouldReturnUpdatedPost() throws Exception {
        // given
        var existingPostId = setupExistingPost().getId();
        var validPostChanges = Instancio.create(UpdatePostDTO.class);

        // when
        var response = mockMvc.perform(put("/posts/" + existingPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validPostChanges)));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPostId))
                .andExpect(jsonPath("$.title").value(validPostChanges.title()))
                .andExpect(jsonPath("$.content").value(validPostChanges.content()));
    }

    @Test
    @DisplayName("Update By Id: should persist updated post")
    void givenExistingPostIdAndValidPostChanges_whenUpdatingById_shouldPersistUpdatedPost() throws Exception {
        // given
        var existingPostId = setupExistingPost().getId();
        var validPostChanges = Instancio.create(UpdatePostDTO.class);

        // when
        mockMvc.perform(put("/posts/" + existingPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validPostChanges)));

        // then
        var findByIdReturn = repository.findById(existingPostId);
        assertTrue(findByIdReturn.isPresent());

        var persistentPost = findByIdReturn.get();
        assertThat(persistentPost.getId()).isEqualTo(existingPostId);
        assertThat(persistentPost.getTitle()).isEqualTo(validPostChanges.title());
        assertThat(persistentPost.getContent()).isEqualTo(validPostChanges.content());
    }

    @Test
    @DisplayName("Update By Id: should return not found when given unknown post id")
    void givenUnknownPostId_whenUpdatingById_shouldReturnNotFoundError() throws Exception {
        // given
        var unknownPostId = 999L;
        var validPostChanges = Instancio.create(UpdatePostDTO.class);

        // when
        var response = mockMvc.perform(put("/posts/" + unknownPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validPostChanges)));

        // then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("error.post.notfound"))
                .andExpect(jsonPath("$.message").value("Post not found."));
    }

    @Test
    @DisplayName("Delete By Id: should return no content")
    void givenExistingPostId_whenDeletingPostById_shouldReturnNoContent() throws Exception {
        // given
        var existingPostId = setupExistingPost().getId();

        // whenc
        var response = mockMvc.perform(delete("/posts/" + existingPostId));

        // then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete By Id: should persist removal")
    void givenExistingPostId_whenDeletingPostById_shouldPersistRemoval() throws Exception {
        // given
        var existingPostId = setupExistingPost().getId();

        // when
        mockMvc.perform(delete("/posts/" + existingPostId));

        // then
        assertThat(repository.findById(existingPostId)).isEmpty();
    }

    @Test
    @DisplayName("Delete By Id: should still return no content even if post id is unknown")
    void givenUnknownPostId_whenDeletingPostById_shouldReturnNotFoundError() throws Exception {
        // given
        var unknownPostId = 999L;

        // when
        var response = mockMvc.perform(delete("/posts/" + unknownPostId));

        // then
        response.andExpect(status().isNoContent());
    }

    private Post setupExistingPost() {
        var generatedPost = Instancio.of(postModel()).create();
        return repository.save(generatedPost);
    }

    private List<Post> setupExistingPosts() {
        var generatedPosts = Instancio.of(postModel()).stream().toList();
        return repository.saveAll(generatedPosts);
    }

    private static Model<Post> postModel() {
        return Instancio.of(Post.class)
                .set(field(Post::getId), null)
                .toModel();
    }
}