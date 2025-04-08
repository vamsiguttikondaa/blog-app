package com.blogapp.services;

import java.util.List;

import com.blogapp.payloads.PostDto;

public interface PostService {
	PostDto createPost(PostDto postDto,Long userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	PostDto findPostById(Integer postId);
	List<PostDto> getPostsByUser(Long userId);
	List<PostDto> getPostsByCategory(Integer categoryId);
	
}
