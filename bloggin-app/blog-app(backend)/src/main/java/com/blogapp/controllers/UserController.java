package com.blogapp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.RegisterDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	
	

	//PUT - update user
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
	    UserDto updatedUser = userService.updateUser(userId, userDto);
	    return ResponseEntity.ok(updatedUser);
	}

	
	
	@GetMapping("/getuser/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id){
		UserDto userDto= userService.getUserById(id);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
}
