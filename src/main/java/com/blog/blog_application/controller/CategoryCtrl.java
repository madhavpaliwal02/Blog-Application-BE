package com.blog.blog_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog_application.payloads.CategoryDto;
import com.blog.blog_application.serviceImpl.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryCtrl {

    private final CategoryService categoryService;

    // Create Category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto catDto) {
        CategoryDto categoryDto = this.categoryService.createCategory(catDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    // get All
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> list = this.categoryService.getAllCategory();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // get category
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") int id) {
        CategoryDto cat = this.categoryService.getCategory(id);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    // update category
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto catDto, @PathVariable("id") int id) {
        CategoryDto cat = this.categoryService.updateCategory(id, catDto);
        return new ResponseEntity<CategoryDto>(cat, HttpStatus.ACCEPTED);
    }

    // delete category
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);
    }
}
