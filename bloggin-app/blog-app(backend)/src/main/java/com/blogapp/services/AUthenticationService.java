package com.blogapp.services;

import com.blogapp.payloads.LoginDto;
import com.blogapp.payloads.LoginResponse;
import com.blogapp.payloads.RegisterDto;
import com.blogapp.payloads.UserDto;

public interface AUthenticationService {
	UserDto registerUser(RegisterDto registerDto);
	LoginResponse login(LoginDto loginDto);
	}

