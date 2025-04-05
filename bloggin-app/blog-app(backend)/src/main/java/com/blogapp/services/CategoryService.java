package com.blogapp.services;

import java.util.List;

import com.blogapp.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(Integer id,CategoryDto categoryDto);
	CategoryDto getCategoryById(Integer id);
	List<CategoryDto> getAllCategories();
	void deleteCategory(Integer id);
}
