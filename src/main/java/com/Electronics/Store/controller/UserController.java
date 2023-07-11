package com.Electronics.Store.controller;

import com.Electronics.Store.Config.AppConstants;
import com.Electronics.Store.Config.GlobalResource;
import com.Electronics.Store.dtos.ApiResponse;
import com.Electronics.Store.dtos.UserDto;
import com.Electronics.Store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = GlobalResource.getLogger(UserController.class);

    //create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){

        String methodName = "createUser()";
        logger.info(methodName+"Called");
        UserDto user = userService.createUser(userDto);
        logger.info(methodName+"user created successfully in database");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    //update

    @PutMapping("/{userId}")

    public ResponseEntity<UserDto> updateUse(@PathVariable String userId,@RequestBody UserDto userDto ){

        UserDto updateUser=userService.updateUser(userDto, userId);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){

        userService.deleteUser(userId);

        return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE_USER, true),HttpStatus.OK);

    }

    //get all
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>>getAllUsers(){

        List<UserDto> allUser = userService.getAllUser();

        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }




}
