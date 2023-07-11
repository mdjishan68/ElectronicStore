package com.Electronics.Store.services;

import com.Electronics.Store.dtos.UserDto;
import com.Electronics.Store.entities.User;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);

    //get all users

    List<UserDto> getAllUser();

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    UserDto updateUser(UserDto userDto);

    //other user specific features

}
