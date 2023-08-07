package com.blog.blog_application.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.blog_application.exception.ResourceNotFoundException;
import com.blog.blog_application.model.User;
import com.blog.blog_application.payloads.UserDto;
import com.blog.blog_application.repositories.UserRepo;
import com.blog.blog_application.serviceImpl.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    /* Add a user */
    @Override
    public UserDto addUser(UserDto userDto) {
        User u = mapToUser(userDto);
        User user = this.userRepo.save(u);
        return mapToUserDto(user);
    }

    /* Get all users */
    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> list = this.userRepo.findAll().stream().map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
        return list;
    }

    /* Get user by id */
    @Override
    public UserDto getUser(int id) {
        User user = getUserHelper(id);
        return mapToUserDto(user);
    }

    /* Update a user */
    @Override
    public UserDto updateUser(UserDto user, int id) {
        User oldUser = this.getUserHelper(id);

        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .about(user.getAbout())
                .build();
        newUser.setId(oldUser.getId());
        this.userRepo.save(newUser);
        return mapToUserDto(newUser);
    }

    /* Delete a user */
    @Override
    public void deleteUser(int id) {
        User oldUser = this.getUserHelper(id);
        this.userRepo.delete(oldUser);
    }

    /* /////////////////////////// Helper Function /////////////////////////// */

    /* Helper Function: Get user */
    public User getUserHelper(int id) {
        return this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    /* Helper Function: User -> UserDto */
    private UserDto mapToUserDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
        // return UserDto.builder()
        // .id(user.getId())
        // .name(user.getName())
        // .email(user.getEmail())
        // .password(user.getPassword())
        // .about(user.getAbout())
        // .build();
    }

    /* Helper Function: UserDto -> User */
    public User mapToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
        // return User.builder()
        // .name(userDto.getName())
        // .email(userDto.getEmail())
        // .password(userDto.getPassword())
        // .about(userDto.getAbout())
        // .build();
    }

}
