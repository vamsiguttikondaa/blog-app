package com.blogapp.services.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.config.AppConstants;
import com.blogapp.entities.Role;
import com.blogapp.entities.User;
import com.blogapp.exceptions.BadCredentialsException;
import com.blogapp.exceptions.ResourceAlreadyExistsException;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.LoginDto;
import com.blogapp.payloads.LoginResponse;
import com.blogapp.payloads.RegisterDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.RoleRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.security.JwtUtilHelper;
import com.blogapp.services.AUthenticationService;
import com.blogapp.utils.DtoConverter;

@Service
public class AuthenticationServiceImpl implements AUthenticationService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DtoConverter dtoConverter;
	@Autowired
	private JwtUtilHelper jwtHelper;
	@Override
	public UserDto registerUser(RegisterDto registerDto) {
		String email=registerDto.getEmail();
	     Optional<User> user=userRepo.findByEmail(email);
	     if(user.isPresent()) {
	    	    System.out.println("User already exists with email: " + email);
	    	    throw new ResourceAlreadyExistsException("email", email);
	    	}
	     User newUser=new User();
   	 newUser.setAbout(registerDto.getAbout());
   	 newUser.setEmail(registerDto.getEmail());
   	 newUser.setName(registerDto.getName());
   	 newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
   	 Role role=roleRepository.findById(AppConstants.ROLE_USER).orElseThrow(()->new ResourceNotFoundException("role", "id",AppConstants.ROLE_USER ));
   	newUser.setRoles(Set.of(role));
   	 User savedUser=userRepo.save(newUser);
	return dtoConverter.toUserDto(savedUser);
	}

	@Override
	public LoginResponse login(LoginDto loginDto) {
		User u=userRepo.findByEmail(loginDto.getEmail()).orElseThrow(()->new ResourceNotFoundException("email", "", loginDto.getEmail()));
		if(!passwordEncoder.matches(loginDto.getPassword(), u.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
		String Token=jwtHelper.generateToken(loginDto.getEmail());
		return new LoginResponse(Token, dtoConverter.toUserDto(u),"logged in succesfull");
	}

}
