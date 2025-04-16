package com.blogapp.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Long userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	PostDto findPostById(Integer postId);
	void deletePost(Integer postId);
	PostResponse getPostsByUser(Long userId,int pageNUmber,int pageSize,Sort sort);
	PostResponse getPostsByCategory(Integer categoryId,int pageNUmber,int pageSize,Sort sort);
	PostResponse getPosts(int pageNUmber,int pageSize,Sort sort);
	List<PostDto> search(String title);
	PostDto uploadImage(Integer postId,MultipartFile file) throws IOException;
	int incrementLike(Integer postId);
	int decrementLike(Integer postId);
	
}
