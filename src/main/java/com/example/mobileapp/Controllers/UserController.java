package com.example.mobileapp.Controllers;

import com.example.mobileapp.Exceptions.UserServiceException;
import com.example.mobileapp.Model.Request.UserDetailsRequestModel;
import com.example.mobileapp.Model.Response.ErrorMessages;
import com.example.mobileapp.Model.Response.UserRest;
import com.example.mobileapp.Services.UserService;
import com.example.mobileapp.Sharred.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    /***
     * We send a post call to create
     * a user
     * Request body takes the json body.
     * After that we create a class to convert json -> java
     *
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
    {
        UserRest returnValue = new UserRest();

        if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "Update user called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}
