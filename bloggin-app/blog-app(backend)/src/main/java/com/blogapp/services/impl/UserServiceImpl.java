package com.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserDto createUser(UserDto user) {
		
		User u = userRepo.save(toUser(user));
		return toUserDto(u);
	}

	@Override
	public UserDto updateUser(Long userId, UserDto user) {
		User existingUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setName(user.getName());
		existingUser.setAbout(user.getAbout());
		User updatedUser= userRepo.save(existingUser);
		return toUserDto(updatedUser);

	}

	@Override
	public UserDto getUserById(Long userId) {
		User existingUser=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));
		return toUserDto(existingUser);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> allUsers=userRepo.findAll();
		List<UserDto> userDtoList=new ArrayList<>();
		for(User u:allUsers) {
			userDtoList.add(toUserDto(u));
		}
		return userDtoList;
	}

	@Override
	public void deleteUser(Long userId) {
		User existingUser=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));
		userRepo.delete(existingUser);

	}
	private User toUser(UserDto userDto) {
//		User user=new User();
//		user.setUserId(userDto.getUserId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		return user;
		return modelMapper.map(userDto,User.class);
	}
	
	
	private UserDto toUserDto(User user) {
//		UserDto userDto=new UserDto();
//		userDto.setUserId(user.getUserId());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		userDto.setName(user.getName());
//		return userDto;
		return modelMapper.map(user,UserDto.class);
	}

}
