package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
