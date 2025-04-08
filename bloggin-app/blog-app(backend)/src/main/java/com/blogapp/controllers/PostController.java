package com.blogapp.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody PostDto postDto,
					@PathVariable Long userId,@PathVariable Integer categoryId){
		PostDto savedPost=postService.createPost(postDto, userId, categoryId);
						return new ResponseEntity<>(new ApiResponse<PostDto>("Post created successfully",true,LocalDateTime.now(),savedPost),HttpStatus.CREATED);
		
	}
	@PutMapping("/update/{postId}")
	public ResponseEntity<ApiResponse<PostDto>> getPost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPost= postService.updatePost(postDto, postId);
		return new ResponseEntity<>(new ApiResponse<PostDto>("updated post successfully", true, LocalDateTime.now(), updatedPost),HttpStatus.OK);
	}
}
