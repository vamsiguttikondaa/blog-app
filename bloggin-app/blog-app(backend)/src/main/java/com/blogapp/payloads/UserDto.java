package com.blogapp.payloads;

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
	 @Email(message="email should be valid")
	 private String email;
	 @NotBlank(message = "Name cannot be blank")
	 @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
	 private String name;
	 @NotBlank(message = "Password is required")
	 @Size(min = 8, message = "Password must be at least 8 characters long")
	 private String password;
	 @Size(min = 10, max = 500, message = "About must be between 10 and 500 characters")
	 @NotBlank(message = "About section cannot be empty")
	 private String about;
}
