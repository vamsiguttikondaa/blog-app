package com.blogapp.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found with email: " + username));
		Set<GrantedAuthority> authorities=user.getRoles().stream().map((role)->new SimpleGrantedAuthority("ROLE_"+role.getRoleName())).collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
		
	}

}
