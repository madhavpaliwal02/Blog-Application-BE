package com.blog.blog_application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_application.model.Category;
import com.blog.blog_application.model.Post;
import com.blog.blog_application.model.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    public List<Post> findByUser(User user);
    public List<Post> findByCategory(Category category);
}
