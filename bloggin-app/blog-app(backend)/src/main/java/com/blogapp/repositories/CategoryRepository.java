package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.entities.Category;
import com.blogapp.payloads.CategoryDto;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
		
		
}
