package com.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category savedCategory=categoryRepo.save(toCategory(categoryDto));
		return toCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
		Category existingCategory=categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", id));
		existingCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		existingCategory.setCategoryName(categoryDto.getCategoryName());
		return toCategoryDto(existingCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Category existingCategory=categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", id));
		return toCategoryDto(existingCategory);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategories=categoryRepo.findAll();
		List<CategoryDto> allCategoriesDto=new ArrayList<>();
		for(Category category:allCategories) {
			allCategoriesDto.add(toCategoryDto(category));
		}
		return allCategoriesDto;
	}

	@Override
	public void deleteCategory(Integer id) {
		Category existingCategory=categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", id));
		categoryRepo.delete(existingCategory);
	}
	private Category toCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
	private CategoryDto toCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

}
