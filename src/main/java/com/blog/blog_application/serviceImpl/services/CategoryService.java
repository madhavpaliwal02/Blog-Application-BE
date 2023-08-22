package com.blog.blog_application.serviceImpl.services;

import java.util.List;

import com.blog.blog_application.payloads.CategoryDto;

public interface CategoryService {
    // create
    public CategoryDto createCategory(CategoryDto catDto);

    // get all
    public List<CategoryDto> getAllCategory();

    // get category
    public CategoryDto getCategory(int id);

    // update category
    public CategoryDto updateCategory(int id, CategoryDto categoryDto);

    // delete category
    public void deleteCategory(int id);
}
