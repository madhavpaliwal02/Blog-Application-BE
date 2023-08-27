package com.blog.blog_application.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.blog_application.exception.UserException;
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
    public UserDto addUser(UserDto userDto) throws UserException {
        // Check Null User
        checkNullUser(userDto);

        // Check existing user
        boolean existUser = this.userRepo.findAll().stream()
                .filter((u) -> u.getName().equals(userDto.getName()) &&
                        u.getEmail().equals(userDto.getEmail()))
                .findAny().isPresent();
        if (existUser)
            throw new UserException("User already exists with given details");

        // Save new User
        User u = mapToUser(userDto);
        // u.setPassword(u.getPassword());
        User user = this.userRepo.save(u);
        return mapToUserDto(user);
    }

    /* Get all users */
    @Override
    public List<UserDto> getAllUsers() throws UserException {
        List<UserDto> list = this.userRepo.findAll().stream().map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
        if (list.size() == 0)
            throw new UserException("No user found !!");
        return list;
    }

    /* Get user by id */
    @Override
    public UserDto getUser(int id) throws UserException {
        User user = this.getUserHelper(id);
        return mapToUserDto(user);
    }

    /* Update a user */
    @Override
    public UserDto updateUser(UserDto user, int id) throws UserException {
        checkNullUser(user);

        User oldUser = this.getUserHelper(id);

        User newUser = this.mapToUser(user);
        newUser.setId(oldUser.getId());
        this.userRepo.save(newUser);
        return mapToUserDto(newUser);
    }

    /* Delete a user */
    @Override
    public void deleteUser(int id) throws UserException {
        User oldUser = this.getUserHelper(id);
        this.userRepo.delete(oldUser);
    }

    /* /////////////////////////// Helper Function /////////////////////////// */

    /* Helper Function: Get user */
    public User getUserHelper(int id) throws UserException {
        if (id == 0)
            throw new UserException("Invalid userId !!");
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new UserException("No user exist with userId: " + id));
        return user;
    }

    /* Check null User */
    public void checkNullUser(UserDto user) throws UserException {
        if (user == null)
            throw new UserException("User Details can't be null");
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
