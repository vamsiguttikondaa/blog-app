package com.blogapp.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public PostDto createPost(PostDto postDto,Long userId,Integer categoryId) {
		UserDto user=userService.getUserById(userId);
		CategoryDto categoryDto=categoryService.getCategoryById(categoryId);
		Post post=new Post();
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
		Post existingPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		existingPost.setContent(postDto.getContent());
		existingPost.setTitle(postDto.getTitle());
		existingPost.setImageName(postDto.getImageName());
		CategoryDto categoryDto=categoryService.getCategoryById(postDto.getCategoryId());
		existingPost.setCategory(dtoConverter.toCategory(categoryDto));
		return dtoConverter.toPostDto(postRepository.save(existingPost));
	}

	@Override
	public PostDto findPostById(Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
