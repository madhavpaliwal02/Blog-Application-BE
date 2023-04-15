package com.blog.blog_application.serviceImpl.services;

import java.util.List;

import com.blog.blog_application.payloads.UserDto;

public interface UserService {
    public UserDto addUser(UserDto user);

    public UserDto getUser(int id);

    public List<UserDto> getAllUsers();

    public UserDto updateUser(UserDto user, int id);

    public void deleteUser(int id);
}
