package com.blogapp.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.services.CategoryService;
import com.blogapp.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;
	//create post
	@Autowired
	private CategoryService categoryService;
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody PostDto postDto,
					@PathVariable Long userId,@PathVariable Integer categoryId){
		PostDto savedPost=postService.createPost(postDto, userId, categoryId);
						return new ResponseEntity<>(new ApiResponse<PostDto>("Post created successfully",true,LocalDateTime.now(),savedPost),HttpStatus.CREATED);
		
	}
	//update post
	@PutMapping("/update/{postId}")
	public ResponseEntity<ApiResponse<PostDto>> getPost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPost= postService.updatePost(postDto, postId);
		return new ResponseEntity<>(new ApiResponse<PostDto>("updated post successfully", true, LocalDateTime.now(), updatedPost),HttpStatus.OK);
	}
	//get single post
	@GetMapping("/getpost/{postId}")
	public ResponseEntity<ApiResponse<PostDto>> findPostById(@PathVariable Integer postId) {
		PostDto existingPost=postService.findPostById(postId);
		return new ResponseEntity<>(new ApiResponse<PostDto>("post retieved succesfully", true, LocalDateTime.now(), existingPost),HttpStatus.OK);
		
	}
	//get all  post by user
	@GetMapping("/getpostsbyuser/{userId}")
	public ResponseEntity<ApiResponse<List<PostDto>>> findPostsByUserId(@PathVariable Long userId){
	    List<PostDto> posts = postService.getPostsByUser(userId);
	    return new ResponseEntity<>(
	        new ApiResponse<>("retrieved posts successfully", true, LocalDateTime.now(), posts),
	        HttpStatus.OK
	    );
	}
	//delete post
	@DeleteMapping("/deletepost/{postId}")
	public ResponseEntity<ApiResponse<PostDto>> deletePOst(@PathVariable Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse<PostDto>("deleted post success", true),HttpStatus.OK);
	}
	//get posts by category
	@GetMapping("/getpostsbycat/{categoryId}")
	public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts=postService.getPostsByCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse<List<PostDto>>(
				"posts retrieved successfully", true, LocalDateTime.now(), posts),HttpStatus.OK);
	}
	@PostMapping("/like/{postId}/incr")
	public ResponseEntity<ApiResponse<Map<String, Object>>> likePost(@PathVariable Integer postId) {
	    int updatedLikes = postService.incrementLike(postId);
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("postId", postId);
	    responseData.put("likes", updatedLikes);

	    ApiResponse<Map<String, Object>> response = new ApiResponse<>(
	        "Post liked successfully", true, LocalDateTime.now(), responseData
	    );

	    return ResponseEntity.ok(response);
	}
	@PostMapping("/like/{postId}/decr")
	public ResponseEntity<ApiResponse<Map<String, Object>>> decrPost(@PathVariable Integer postId) {
	    int updatedLikes = postService.decrementLike(postId);
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("postId", postId);
	    responseData.put("likes", updatedLikes);

	    ApiResponse<Map<String, Object>> response = new ApiResponse<>(
	        "Post unliked successfully", true, LocalDateTime.now(), responseData
	    );

	    return ResponseEntity.ok(response);
	}


}
