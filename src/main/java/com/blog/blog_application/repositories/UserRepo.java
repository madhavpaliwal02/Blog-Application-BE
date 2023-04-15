package com.blog.blog_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog_application.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
