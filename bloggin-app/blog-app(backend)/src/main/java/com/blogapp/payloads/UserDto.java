package com.blogapp.payloads;

import java.util.Set;

import com.blogapp.entities.Post;
import com.blogapp.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
	
	
	 private Long userId;
	
	 private String email;
	 
	 private String name;

	
	 private String about;
	 private Set<Role> roles;
	
}
