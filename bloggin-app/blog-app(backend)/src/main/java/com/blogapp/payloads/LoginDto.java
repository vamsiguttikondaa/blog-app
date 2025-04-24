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
public class LoginDto {
	@NotBlank
	 @Email(message="email should be valid")
	private String email;
	@NotBlank(message = "Password is required")
	 @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
}
