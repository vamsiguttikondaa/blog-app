package com.blogapp.services;

import java.util.List;

import com.blogapp.entities.User;
import com.blogapp.payloads.UserDto;

public interface UserService {
	 	UserDto createUser(UserDto user);
	 	UserDto updateUser(Long userId,UserDto user);
	 	UserDto getUserById(Long userId);
	 	List<UserDto> getAllUsers();
	 	void deleteUser(Long userId);
}
