package com.luispdev.javaservicetemplate.repository;

import com.luispdev.javaservicetemplate.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
