package com.blogapp.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blogapp.entities.User;
import com.blogapp.entities.Post;
import com.blogapp.entities.Category;
import com.blogapp.payloads.UserDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.CategoryDto;

@Component
public class DtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public User toUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public Post toPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    public PostDto toPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Category toCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
