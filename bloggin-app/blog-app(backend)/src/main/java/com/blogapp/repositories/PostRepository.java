package com.blogapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	Page<Post> findByUser(User user,Pageable pageable);
	Page<Post> findByCategory(Category category,Pageable pageable);
	List<Post> searchByTitleContaining(String keyword);
	
}
