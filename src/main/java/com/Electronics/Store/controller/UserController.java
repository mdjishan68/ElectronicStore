package com.Electronics.Store.controller;

import com.Electronics.Store.Config.AppConstants;
import com.Electronics.Store.Config.GlobalResource;
import com.Electronics.Store.dtos.ApiResponse;
import com.Electronics.Store.dtos.ImageResponse;
import com.Electronics.Store.dtos.PageableResponse;
import com.Electronics.Store.dtos.UserDto;
import com.Electronics.Store.services.ServiceFile;
import com.Electronics.Store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceFile serviceFile;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = GlobalResource.getLogger(UserController.class);

    //create
   /*
     @param userDto
     @return ResponseEntity<UserDto>
     @auther Md Jishan
     @apiNote create User
   */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        String methodName = "createUser()";
        logger.info(methodName+"Called");
        UserDto user = userService.createUser(userDto);
        logger.info(methodName+"user created successfully in database");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //update
    /*
     @param userDto
     @param userId
     @return ResponseEntity<UserDto>V8
     @auther Md Jishan
     @apiNote upadte User
   */
    @PutMapping("/{userId}")

    public ResponseEntity<UserDto> updateUse(@Valid @PathVariable String userId,@RequestBody UserDto userDto ){

        UserDto updateUser=userService.updateUser(userDto, userId);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    //delete
    /*
     @param userId
     @return ResponseEntity<String>
     @auther Md Jishan
     @apiNote delete user with given Id
   */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){

        userService.deleteUser(userId);

        return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE_USER, true),HttpStatus.OK);

    }
    //get all
    /*
     @return ResponseEntity<UserDto>
     @auther Md Jishan
     @apiNote get all Users
   */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>>getAllUsers(

            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);


    }
    //get single Id
    /*
     @param userId
     @return ResponseEntity<UserDto>
     @auther Md Jishan
     @apiNote get user by Id
   */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("sending request to service for retrieving with user with Id: {}", userId);
        UserDto user=userService.getUserById(userId);
        logger.info("successfully retrieved user: {}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserEmail(@PathVariable String email) {
        logger.info("sending request to service for retrieving with user with Id: {}");
        UserDto user = userService.getUserByEmail(email);
        logger.info("successfully retrieved user: {}", user);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


        //get by search
        @GetMapping("/search/{keywords}")
        public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){

            return new ResponseEntity<List<UserDto>>(userService.searchUser(keywords), HttpStatus.OK);
        }
        //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {

       String imageName = serviceFile.uploadFile(image,imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);


        ImageResponse imageResponse = ImageResponse.builder().message("image uploaded successfully !!").imageName(imageName).success(true).status(HttpStatus.CREATED).build();
       return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


    }
        //server user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);

        InputStream resource = serviceFile.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());



    }

    }