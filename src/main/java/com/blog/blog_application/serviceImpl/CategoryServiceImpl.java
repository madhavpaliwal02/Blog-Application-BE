package com.blog.blog_application.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.blog_application.exception.ResourceNotFoundException;
import com.blog.blog_application.model.Category;
import com.blog.blog_application.payloads.CategoryDto;
import com.blog.blog_application.repositories.CategoryRepo;
import com.blog.blog_application.serviceImpl.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto catDto) {
        Category cat = this.categoryRepo.save(mapToCategory(catDto));
        return mapToCategoryDto(cat);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> list = this.categoryRepo.findAll().stream()
                .map((cat) -> mapToCategoryDto(cat)).collect(Collectors.toList());
        return list;
    }

    @Override
    public CategoryDto getCategory(int id) {
        Category cat = this.categoryHelper(id);
        return mapToCategoryDto(cat);
    }

    @Override
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category cat = mapToCategory(categoryDto);
        cat.setCategoryId(id);
        return mapToCategoryDto(this.categoryRepo.save(cat));
    }

    @Override
    public void deleteCategory(int id) {
        Category cat = categoryHelper(id);
        this.categoryRepo.delete(cat);
    }

    private Category categoryHelper(int id) {
        return this.categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));
    }

    private Category mapToCategory(CategoryDto catDto) {
        return this.mapper.map(catDto, Category.class);
    }

    private CategoryDto mapToCategoryDto(Category cat) {
        return this.mapper.map(cat, CategoryDto.class);
    }

}
