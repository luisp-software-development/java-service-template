package com.luispdev.javaservicetemplate.post;

import com.luispdev.javaservicetemplate.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
