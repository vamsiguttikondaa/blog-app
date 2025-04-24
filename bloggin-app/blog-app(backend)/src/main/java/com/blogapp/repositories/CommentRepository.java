package com.blogapp.repositories;

import com.blogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByPost_PostId(Integer postId);

}
