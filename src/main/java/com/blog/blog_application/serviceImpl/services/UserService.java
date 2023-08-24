package com.blog.blog_application.serviceImpl.services;

import java.util.List;

import com.blog.blog_application.exception.UserException;
import com.blog.blog_application.payloads.UserDto;

public interface UserService {
    public UserDto addUser(UserDto user) throws UserException;

    public UserDto getUser(int id) throws UserException;

    public List<UserDto> getAllUsers() throws UserException;

    public UserDto updateUser(UserDto user, int id) throws UserException;

    public void deleteUser(int id) throws UserException;
}
