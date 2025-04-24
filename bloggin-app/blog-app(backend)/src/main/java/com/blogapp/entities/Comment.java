package com.blogapp.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	@Column(name="comment",nullable = false)
	private String Content;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
		
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
}
