package com.blog.blog_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_application.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
