package com.blogapp.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.PostRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.CategoryService;
import com.blogapp.services.FileUploadService;
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
	@Autowired
	private FileUploadService fileUploadService;

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
	//get all posts by user
	@Override
	public PostResponse getPostsByUser(Long userId,int pageNUmber,int pageSize,Sort sort ) {
		UserDto user = userService.getUserById(userId);
		Pageable pageable=PageRequest.of(pageNUmber, pageSize, sort);
		Page<Post> pagePost = postRepository.findByUser(dtoConverter.toUser(user),pageable);
		List<PostDto> postDtoByUser = pagePost.getContent().stream().map(dtoConverter::toPostDto)
										.collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		  postResponse.setContent(postDtoByUser);
		    postResponse.setPageNumber(pagePost.getNumber());
		    postResponse.setPageSize(pagePost.getSize());
		    postResponse.setTotalElements(pagePost.getTotalElements());
		    postResponse.setTotalPages(pagePost.getTotalPages());
		    postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}
	
	// get posts by category
	@Override
	public PostResponse getPostsByCategory(Integer categoryId,int pageNUmber,int pageSize,Sort sort) {
		CategoryDto category = categoryService.getCategoryById(categoryId);
		Pageable pageable=PageRequest.of(pageNUmber,pageSize,sort);
		Page<Post> pagePost = postRepository.findByCategory(dtoConverter.toCategory(category),pageable);
		List<PostDto> postsDto = pagePost.getContent().stream().map(dtoConverter::toPostDto).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		  postResponse.setContent(postsDto);
		    postResponse.setPageNumber(pagePost.getNumber());
		    postResponse.setPageSize(pagePost.getSize());
		    postResponse.setTotalElements(pagePost.getTotalElements());
		    postResponse.setTotalPages(pagePost.getTotalPages());
		    postResponse.setLastPage(pagePost.isLast());
		return postResponse;
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

	@Override
	public PostResponse getPosts(int pageNUmber,int pageSize,Sort sort) {
		Pageable pageable=PageRequest.of(pageNUmber, pageSize,sort);
		Page<Post> pagePost=postRepository.findAll(pageable);
		List<PostDto> postsDto=pagePost.getContent().stream().
				map((post)->dtoConverter.toPostDto(post)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		  postResponse.setContent(postsDto);
		    postResponse.setPageNumber(pagePost.getNumber());
		    postResponse.setPageSize(pagePost.getSize());
		    postResponse.setTotalElements(pagePost.getTotalElements());
		    postResponse.setTotalPages(pagePost.getTotalPages());
		    postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> search(String title) {
		List<Post> posts=postRepository.searchByTitleContaining(title);
		List<PostDto> postsDto=posts.stream().map(dtoConverter::toPostDto).collect(Collectors.toList());
		
		return postsDto;
	}

	@Override
	public PostDto uploadImage(Integer postId, MultipartFile file) throws IOException {
		Post existingPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		String imageUrl=fileUploadService.uploadImage(file);
		existingPost.setImageName(imageUrl);
		return dtoConverter.toPostDto(postRepository.save(existingPost));
	}
}
