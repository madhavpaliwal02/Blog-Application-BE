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

import com.blog.blog_application.exception.UserException;
import com.blog.blog_application.payloads.ApiResponse;
import com.blog.blog_application.payloads.UserDto;
import com.blog.blog_application.serviceImpl.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserCtrl {

    private final UserService userService;

    /* Add a user */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) throws UserException {
        UserDto user = this.userService.addUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /* Get all users */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() throws UserException {
        List<UserDto> list = this.userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    /* Get a user */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id) throws UserException {
        UserDto user = this.userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    /* Update a user */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") int id, @RequestBody UserDto userDto)
            throws UserException {
        UserDto user = this.userService.updateUser(userDto, id);
        return ResponseEntity.ok(user);
    }

    /* Delete a user */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int id) throws UserException {
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Successfully Deleted...", true), HttpStatus.OK);

    }
}
