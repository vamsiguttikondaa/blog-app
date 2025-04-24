package com.blogapp.controllers;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.UserService;
import com.blogapp.services.PostService;
import com.blogapp.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    // ------------------ USERS ------------------

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    // ------------------ POSTS ------------------

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
    		@RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
			@RequestParam (defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
			@RequestParam(defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    		) {
    	Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
			Sort.by(sortBy).descending();
    		PostResponse pagePosts=postService.getPosts(pageNumber,pageSize,sort);
        return ResponseEntity.ok(pagePosts);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted by admin", true));
    }

    // You could add more like approving/unapproving posts

    // ------------------ CATEGORIES ------------------

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
    }
}
