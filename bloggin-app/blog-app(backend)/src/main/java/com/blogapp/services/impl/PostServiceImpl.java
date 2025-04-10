package com.blogapp.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.PostRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.CategoryService;
import com.blogapp.services.PostService;
import com.blogapp.services.UserService;
import com.blogapp.utils.DtoConverter;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private DtoConverter dtoConverter;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Integer categoryId) {
		UserDto user = userService.getUserById(userId);
		CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
		Post post = new Post();
		post.setContent(postDto.getContent());
		post.setLikes(0);
		post.setImageName("defualt.jpg");
		post.setAddedDate(LocalDateTime.now());
		post.setTitle(postDto.getTitle());
		post.setUser(dtoConverter.toUser(user));
		post.setCategory(dtoConverter.toCategory(categoryDto));
		return dtoConverter.toPostDto(postRepository.save(post));
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		existingPost.setContent(postDto.getContent());
		existingPost.setTitle(postDto.getTitle());
		existingPost.setImageName(postDto.getImageName());
		CategoryDto categoryDto = categoryService.getCategoryById(postDto.getCategoryId());
		existingPost.setCategory(dtoConverter.toCategory(categoryDto));
		return dtoConverter.toPostDto(postRepository.save(existingPost));
	}

	@Override
	public PostDto findPostById(Integer postId) {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		return dtoConverter.toPostDto(existingPost);
	}

	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		UserDto user = userService.getUserById(userId);
		List<Post> postsByUser = postRepository.findByUser(dtoConverter.toUser(user));
		List<PostDto> postDtoByUser = postsByUser.stream().map((post) -> dtoConverter.toPostDto(post))
				.collect(Collectors.toList());
		return postDtoByUser;
	}

	// get posts by category
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		CategoryDto category = categoryService.getCategoryById(categoryId);
		List<Post> posts = postRepository.findByCategory(dtoConverter.toCategory(category));
		List<PostDto> postsDto = posts.stream().map(dtoConverter::toPostDto).collect(Collectors.toList());
		return postsDto;
	}

	// delete post
	@DeleteMapping("/deletepost/{postId}")
	public void deletePost(@PathVariable Integer postId) {
		postRepository.deleteById(postId);
	}

	@Override
	public int incrementLike(Integer postId) {
		Post existingPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		int existingLikes=existingPost.getLikes();
		existingPost.setLikes((existingLikes+1));
		postRepository.save(existingPost);
		return existingLikes+1;
	}

	@Override
	public int decrementLike(Integer postId) {
		Post existingPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		int currentLikes=Math.max(existingPost.getLikes()-1,0);
		existingPost.setLikes(currentLikes);
		postRepository.save(existingPost);
		return currentLikes;
	}
}
