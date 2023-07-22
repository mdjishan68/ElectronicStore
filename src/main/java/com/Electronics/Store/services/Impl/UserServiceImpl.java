package com.Electronics.Store.services.Impl;

import com.Electronics.Store.Config.AppConstants;
import com.Electronics.Store.dtos.PageableResponse;
import com.Electronics.Store.dtos.UserDto;
import com.Electronics.Store.entities.User;
import com.Electronics.Store.exception.ResourceNotFoundException;
import com.Electronics.Store.repositories.UserRepository;
import com.Electronics.Store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //create variable for logger
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String methodName = "createUser()";
        logger.info(methodName+"Called");
        //generate userId in String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto to entity
        //User user =dtoToEntity(userDto);
        //User saveUser = userRepository.save(user);
        //dto to entity
        //UserDto newDto=entityToDto(saveUser);

        User user = mapper.map(userDto, User.class);
        User savedUser =userRepository.save(user);
        logger.info("Sending response to controller of user created successfully: {}", savedUser.getName());
        UserDto newDto = mapper.map(user, UserDto.class);
        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
       logger.info("Sending request to repository for updating user: {} with ID:{}",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));

        user.setName(userDto.getName());
        //email update
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        //UserDto updatedDto = entityToDto(updatedUser)
        logger.info("Sending response to controller of user updated successfully: {} with ID: {}", updatedUser.getName(),userId);
        UserDto updatedDto = mapper.map(updatedUser, UserDto.class);

        return updatedDto;

    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Sending request to repository for deleting user with Id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        //delete user profile image
        userRepository.delete(user);
        logger.info("Sending response to controller of user deleted successfully with Id: {}", userId);

    }



    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")?(Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

        List<User> users = page.getContent();

        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        PageableResponse<UserDto> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found by given id"));

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with given email id and password"));

        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }


    private User dtoToEntity(UserDto userDto) {
        /*User user=User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();*/

        return mapper.map(userDto, User.class);


    }

    private UserDto entityToDto(User saveUser){
        /*UserDto userDto=UserDto.builder()
                .userId(saveUser.getUserId())
                .name(saveUser.getName())
                .Email(saveUser.getEmail())
                .password(saveUser.getEmail())
                .about(saveUser.getAbout())
                .gender(saveUser.getGender())
                .imageName(saveUser.getImageName()).build();*/
        return mapper.map(saveUser, UserDto.class);
        //                  source    Destination
    }

}
