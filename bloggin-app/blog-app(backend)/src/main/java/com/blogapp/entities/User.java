package com.blogapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;

	    @Column(name = "email", nullable = false, unique = true, length = 150)
	    private String email;

	    @Column(name = "user_name", nullable = false, length = 100)
	    private String name;

	    @Column(name = "password", nullable = false, length = 255) // Store hashed password
	    private String password;
	    private String about;
	

}
