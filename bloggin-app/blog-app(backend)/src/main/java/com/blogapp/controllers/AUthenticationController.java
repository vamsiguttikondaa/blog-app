package com.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.LoginDto;
import com.blogapp.payloads.LoginResponse;
import com.blogapp.payloads.RegisterDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.AUthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AUthenticationController {
	
	@Autowired
	private AUthenticationService authenticationService;
	@PostMapping("/register")

	public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto registerDto){
		UserDto registeredU=authenticationService.registerUser(registerDto);
		
		return new ResponseEntity<>(registeredU,HttpStatus.OK);
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDto loginDto){
		return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
	}

}
