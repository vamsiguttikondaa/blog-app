package com.blogapp.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	    
	    @OneToMany(mappedBy = "user",orphanRemoval = true,cascade = CascadeType.ALL)
	    private Set<Post> posts;

}
